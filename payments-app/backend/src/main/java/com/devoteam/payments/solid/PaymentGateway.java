package com.devoteam.payments.solid;

// BUG (ISP): interface "gorda" - um gateway que so faz pagamentos e
// obrigado a implementar refund()/chargeback() mesmo que nunca os suporte.
// Devia haver interfaces mais pequenas e especificas.
public interface PaymentGateway {
  void charge(double amount);
  void refund(double amount);
  void chargeback(String reason);
}
