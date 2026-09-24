package com.acme.payments.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true, nullable=false)
    private String iban;
    @Column(nullable=false)
    private BigDecimal balance = BigDecimal.ZERO;
    @Version
    private Long version;

    protected Account() {}
    public Account(String iban, BigDecimal balance){ this.iban=iban; this.balance=balance; }
    public Long getId(){ return id; }
    public String getIban(){ return iban; }
    public BigDecimal getBalance(){ return balance; }
    public Long getVersion(){ return version; }
    public void setBalance(BigDecimal balance){ this.balance=balance; }
}
