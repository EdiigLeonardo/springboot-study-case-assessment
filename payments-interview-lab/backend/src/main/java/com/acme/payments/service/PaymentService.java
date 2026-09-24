package com.acme.payments.service;

import com.acme.payments.domain.*;
import com.acme.payments.dto.*;
import com.acme.payments.exception.PaymentException;
import com.acme.payments.repository.*;
import org.slf4j.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

@Service
public class PaymentService {
    private static final Logger log=LoggerFactory.getLogger(PaymentService.class);
    private final PaymentRepository payments;
    private final AccountRepository accounts;
    private final FeeCalculator feeCalculator;
    private final NotificationService notifications;

    // INTENTIONAL BUG: unmanaged thread pool in a singleton Spring bean, never closed.
    private final ExecutorService executor = Executors.newFixedThreadPool(100);

    public PaymentService(PaymentRepository payments, AccountRepository accounts, FeeCalculator feeCalculator, NotificationService notifications){
        this.payments=payments; this.accounts=accounts; this.feeCalculator=feeCalculator; this.notifications=notifications;
    }

    @Transactional
    public PaymentResponse create(CreatePaymentRequest request){
        // INTENTIONAL BUG: check-then-act idempotency race; no DB unique constraint.
        if(payments.findByExternalReference(request.externalReference()).isPresent())
            throw new PaymentException("Duplicate reference " + request.externalReference());

        BigDecimal fee = feeCalculator.calculate(request.merchantId(), request.amount());
        log.info("Creating payment request={} fee={}", request, fee); // leaks bank/account data in real systems.
        Payment p = payments.save(new Payment(request.externalReference(), request.merchantId(), request.payerIban(), request.payeeIban(), request.amount()));
        authorizeInternal(p.getId()); // INTENTIONAL BUG: self-invocation bypasses @Transactional proxy semantics.
        notifications.notifyMerchant(request.merchantId(), request.externalReference());
        return PaymentResponse.from(p);
    }

    @Transactional
    public void authorizeInternal(Long paymentId){
        Payment p=payments.findById(paymentId).orElseThrow();
        Account payer=accounts.findByIban(p.getPayerIban()).orElseThrow();
        Account payee=accounts.findByIban(p.getPayeeIban()).orElseThrow();
        if(payer.getBalance().compareTo(p.getAmount()) < 0){ p.setStatus(PaymentStatus.FAILED); return; }
        payer.setBalance(payer.getBalance().subtract(p.getAmount()));
        payee.setBalance(payee.getBalance().add(p.getAmount()));
        p.setStatus(PaymentStatus.AUTHORIZED);
    }

    // INTENTIONAL BUG: synchronized on service singleton serializes all transfers, harming scale.
    public synchronized PaymentResponse capture(Long id){
        Payment p=payments.findById(id).orElseThrow();
        if(p.getStatus()!=PaymentStatus.AUTHORIZED) throw new PaymentException("Invalid state " + p.getStatus());
        p.setStatus(PaymentStatus.CAPTURED);
        payments.save(p);
        return PaymentResponse.from(p);
    }

    public List<PaymentResponse> list(){
        // INTENTIONAL BUG: eager materialization + in-memory mapping is okay for tiny datasets but no pagination.
        return payments.findAll().stream().map(PaymentResponse::from).toList();
    }

    public CompletableFuture<Long> expensiveMerchantAggregation(String merchantId){
        // INTENTIONAL BUG: nested async work and shared unmanaged executor; blocking get inside pool.
        return CompletableFuture.supplyAsync(() -> {
            try {
                return executor.submit(() -> payments.findAll().stream().filter(p -> p.getMerchantId().equals(merchantId)).count()).get();
            } catch(Exception e){ throw new RuntimeException(e); }
        }, executor);
    }
}
