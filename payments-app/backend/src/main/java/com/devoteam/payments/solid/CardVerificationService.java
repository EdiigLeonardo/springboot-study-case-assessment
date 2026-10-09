package com.devoteam.payments.solid;
import org.springframework.stereotype.Service;

@Service
public class CardVerificationService {
  public boolean isValid(String cardNumber) {
    return cardNumber != null && cardNumber.length() == 16;
  }
}