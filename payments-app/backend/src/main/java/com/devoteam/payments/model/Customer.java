package com.devoteam.payments.model;

// BUG (dedup / equals-hashCode): sem equals()/hashCode(), duas instancias
// com o MESMO nome sao sempre "diferentes" para o Java - .distinct() no
// Stream nunca remove ninguem, mesmo com nomes repetidos.
public class Customer {
  private final String name;
  public Customer(String name) { this.name = name; }
  public String getName() { return name; }
}
