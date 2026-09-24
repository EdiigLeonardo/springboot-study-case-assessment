package com.devoteam.payments.solid;
import org.springframework.stereotype.Service;

// BUG (DIP): dependencia direta de uma classe CONCRETA (criada com "new"),
// em vez de depender de uma abstracao injetada pelo Spring. Impossivel
// trocar a implementacao ou mockar em testes.
@Service
public class ReportService {
  private final LegacyPaymentRepository repository = new LegacyPaymentRepository();
  public void generateReport() { repository.findAll(); }
}
class LegacyPaymentRepository { java.util.List<Object> findAll() { return java.util.List.of(); } }
