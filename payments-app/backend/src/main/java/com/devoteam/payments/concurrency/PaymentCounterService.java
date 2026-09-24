package com.devoteam.payments.concurrency;
import org.springframework.stereotype.Service;

// BUG (singleton + concorrencia): @Service e singleton por defeito - esta
// instancia UNICA e partilhada por TODOS os pedidos em simultaneo. "count"
// e um int normal, nao sincronizado/atomico -> sob carga concorrente,
// incrementos perdem-se (race condition classica).
@Service
public class PaymentCounterService {
  private int count = 0;
  public void increment() { count++; }
  public int getCount() { return count; }
}
