package com.devoteam.payments.solid;

public interface PaymentMethod {
  double calculateFee(double amount);
}
