package com.devoteam.payments.solid;
import org.springframework.stereotype.Service;

@Service
public class PaymentServicePersistence {
  public void savePayment(String cardNumber, double amount) {
    System.out.println("A gravar pagamento na BD...");
  }
}