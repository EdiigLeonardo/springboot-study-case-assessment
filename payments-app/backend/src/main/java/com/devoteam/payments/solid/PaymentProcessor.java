package com.devoteam.payments.solid;
import org.springframework.stereotype.Component;

@Component
public class PaymentProcessor {
  public double calculateFee(PaymentMethod paymentMethod, double amount) {
    return paymentMethod.calculateFee(amount);
  }
}
