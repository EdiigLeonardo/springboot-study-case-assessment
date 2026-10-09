package com.devoteam.payments.solid;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceNotification {
  public void sendConfirmationEmail() {
    System.out.println("A enviar email de confirmacao...");
  }
}