package com.acme.payments.service;
import java.math.BigDecimal;
public interface FeeCalculator { BigDecimal calculate(String merchantId, BigDecimal amount); }
