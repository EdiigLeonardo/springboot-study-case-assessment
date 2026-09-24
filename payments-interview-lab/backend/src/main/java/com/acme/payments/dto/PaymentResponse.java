package com.acme.payments.dto;
import com.acme.payments.domain.Payment;
import com.acme.payments.domain.PaymentStatus;
import java.math.BigDecimal;
public record PaymentResponse(Long id, String externalReference, BigDecimal amount, PaymentStatus status) {
    public static PaymentResponse from(Payment p){ return new PaymentResponse(p.getId(),p.getExternalReference(),p.getAmount(),p.getStatus()); }
}
