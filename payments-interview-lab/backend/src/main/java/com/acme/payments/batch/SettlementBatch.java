package com.acme.payments.batch;

import com.acme.payments.domain.*;
import com.acme.payments.repository.PaymentRepository;
import org.slf4j.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@Component
public class SettlementBatch {
    private static final Logger log=LoggerFactory.getLogger(SettlementBatch.class);
    private final PaymentRepository repo;
    private final SimpleDateFormat fmt;
    public SettlementBatch(PaymentRepository repo, SimpleDateFormat fmt){this.repo=repo; this.fmt=fmt;}

    @Scheduled(fixedDelay=60_000)
    @Transactional
    public void settle(){
        List<Payment> items=repo.findByStatusAndCreatedAtBefore(PaymentStatus.CAPTURED, Instant.now().minusSeconds(10));
        // INTENTIONAL BUGS: huge transaction, no pagination/chunking, no distributed lock, no idempotency token.
        for(Payment p:items){
            log.info("Settling {} at {}", p.getId(), fmt.format(new Date()));
            if(p.getExternalReference().contains("POISON")) throw new IllegalStateException("Poison payment");
            simulateRemoteClearing(p);
            p.setStatus(PaymentStatus.SETTLED);
        }
    }

    private void simulateRemoteClearing(Payment p){
        try { Thread.sleep(50); } catch(InterruptedException e){ /* INTENTIONAL BUG: interrupt swallowed */ }
    }
}
