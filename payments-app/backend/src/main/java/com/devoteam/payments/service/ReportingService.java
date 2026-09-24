package com.devoteam.payments.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportingService {
  // BUG (@Transactional REQUIRES_NEW mal usado): abre uma transacao NOVA e
  // INDEPENDENTE dentro de outra ja em curso (ver PaymentController). Se a
  // transacao exterior fizer rollback depois, isto AQUI ja fez commit e
  // fica gravado - inconsistencia entre "pagamento cancelado" e "log de
  // auditoria diz que aconteceu".
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void auditLog(String message) {
    System.out.println("AUDIT (commitado logo, independente do resto): " + message);
  }
}
