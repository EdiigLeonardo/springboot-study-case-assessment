package com.devoteam.payments.model;
import java.util.List;

// BUG (imutabilidade): parece imutavel (campos final, sem setters) mas
// getTags() devolve a lista INTERNA diretamente - quem chamar
// getTags().add(...) muda o objeto "imutavel" por fora, sem passar por
// nenhum metodo dele.
public final class PaymentDetails {
  private final String cardLast4;
  private final List<String> tags;
  public PaymentDetails(String cardLast4, List<String> tags) { this.cardLast4 = cardLast4; this.tags = tags; }
  public String getCardLast4() { return cardLast4; }
  public List<String> getTags() { return tags; }
}
