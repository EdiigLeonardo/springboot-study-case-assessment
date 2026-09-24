package com.acme.payments.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
public class Payment {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String externalReference;
    private String merchantId;
    private String payerIban;
    private String payeeIban;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    protected Payment() {}
    public Payment(String externalReference, String merchantId, String payerIban, String payeeIban, BigDecimal amount){
        this.externalReference=externalReference; this.merchantId=merchantId; this.payerIban=payerIban; this.payeeIban=payeeIban;
        this.amount=amount; this.status=PaymentStatus.CREATED; this.createdAt=Instant.now(); this.updatedAt=Instant.now();
    }
    public Long getId(){return id;} public String getExternalReference(){return externalReference;} public String getMerchantId(){return merchantId;}
    public String getPayerIban(){return payerIban;} public String getPayeeIban(){return payeeIban;} public BigDecimal getAmount(){return amount;}
    public PaymentStatus getStatus(){return status;} public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;}
    public void setStatus(PaymentStatus status){this.status=status; this.updatedAt=Instant.now();}
}
