package com.devoteam.payments.solid;
import org.springframework.stereotype.Component;

// BUG (OCP): para suportar um novo tipo de pagamento (ex.: PIX) tens de
// EDITAR este metodo. Devia ser extensivel via polimorfismo/Strategy sem
// tocar no codigo existente.
@Component
public class PaymentProcessor {
  public double calculateFee(String type, double amount) {
    switch (type) {
      case "CREDIT_CARD": return amount * 0.02;
      case "DEBIT_CARD": return amount * 0.01;
      case "BANK_TRANSFER": return 0;
      default: throw new IllegalArgumentException("tipo desconhecido");
    }
  }
}
