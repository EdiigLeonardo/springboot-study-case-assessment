package com.devoteam.payments.solid;

public class RefundablePayment {
  public void refund(double amount) { System.out.println("A reembolsar " + amount); }
}

// BUG (LSP): a subclasse quebra o contrato do pai - quem recebe um
// RefundablePayment e chama refund() espera que funcione; esta subclasse
// lanca excecao em vez disso. Nao e substituivel pelo tipo base.
class NonRefundablePayment extends RefundablePayment {
  @Override
  public void refund(double amount) { throw new UnsupportedOperationException("este pagamento nao e reembolsavel"); }
}
