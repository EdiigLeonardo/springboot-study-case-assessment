package com.acme.payments.service;
import org.springframework.stereotype.Component;
import java.math.*;
@Component
public class LegacyFeeCalculator implements FeeCalculator {
    public BigDecimal calculate(String merchantId, BigDecimal amount){
        if(merchantId.startsWith("VIP")) return amount.multiply(new BigDecimal("0.005"));
        return amount.multiply(new BigDecimal("0.02"));
    }
}
