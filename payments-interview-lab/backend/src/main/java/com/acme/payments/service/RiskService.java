package com.acme.payments.service;
import com.acme.payments.domain.Payment;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class RiskService {
    // INTENTIONAL DESIGN SMELL: too many reasons to change, violates SRP/OCP and hardcodes strategies.
    public int score(Payment p){
        int score=0;
        if(p.getAmount().doubleValue()>1000) score+=40;
        if(p.getMerchantId().startsWith("NEW")) score+=30;
        if(p.getPayerIban().startsWith("XX")) score+=100;
        return score;
    }
    public String buildCsv(Payment p){ return p.getId()+","+p.getMerchantId()+","+p.getAmount(); }
    public void audit(Payment p){ System.out.println("AUDIT " + p.getId()); }
}
