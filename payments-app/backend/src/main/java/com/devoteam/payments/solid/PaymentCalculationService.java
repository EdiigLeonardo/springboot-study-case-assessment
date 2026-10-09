package com.devoteam.payments.solid;
import org.springframework.stereotype.Service;

@Service
public class PaymentCalculationService {
  public double calculateFee(double amount) {
    return amount * 0.02;
  }
}