package com.devoteam.payments.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PaymentLogger {
  private static final Logger log = LoggerFactory.getLogger(PaymentLogger.class);

  public void charge(String cardNumber) {
    try {
      if (cardNumber.length() != 16) throw new IllegalArgumentException("cartao invalido: " + cardNumber);
    } catch (Exception e) {
      // BUG (log+throw): loga aqui E relanca - quem apanhar isto mais acima
      // vai logar OUTRA VEZ o mesmo erro (duplica entradas de log, dificulta debug).
      // BUG (dados sensiveis): o numero do cartao completo vai parar aos
      // logs em texto simples - inaceitavel num sistema de pagamentos (PCI).
      log.error("Falha ao processar cartao " + cardNumber, e);
      throw e;
    }
  }
}
