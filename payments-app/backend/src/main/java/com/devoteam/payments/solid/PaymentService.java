package com.devoteam.payments.solid;
import org.springframework.stereotype.Service;

// BUG (SRP): uma classe, 4 responsabilidades (validar, calcular taxa,
// gravar, notificar). Qualquer alteracao a uma delas obriga a mexer aqui -
// e a testar as outras 3 de novo.
@Service
public class PaymentService {
  public void process(String cardNumber, double amount) {
    if (cardNumber == null || cardNumber.length() != 16) throw new IllegalArgumentException("cartao invalido");
    double fee = amount * 0.02;
    System.out.println("A gravar pagamento na BD...");
    System.out.println("A enviar email de confirmacao...");
  }
}
