package com.devoteam.payments.service;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
  // BUG (@Async silencioso): metodo void assincrono - se lancar excecao,
  // NINGUEM saber. Sem AsyncUncaughtExceptionHandler configurado, o erro
  // desaparece nos logs do thread pool, o chamador nunca fica a saber que a
  // notificacao falhou.
  @Async
  public void sendConfirmation(String email) {
    if (email == null) throw new IllegalArgumentException("email em falta");
    System.out.println("A enviar confirmacao para " + email);
  }
}
