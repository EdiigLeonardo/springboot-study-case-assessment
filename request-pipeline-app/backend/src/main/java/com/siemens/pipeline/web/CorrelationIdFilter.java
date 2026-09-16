package com.siemens.pipeline.web;
import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.UUID;

// BUG: mete o correlation-id no MDC (usado pelo logger) mas nunca chama
// MDC.clear() - se este pedido falhar antes do fim, ou se outro filtro sem
// @Order correr por cima, o proximo pedido na MESMA thread pode logar com
// o correlation-id ERRADO (do pedido anterior).
@Component
public class CorrelationIdFilter implements Filter {
  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    MDC.put("correlationId", UUID.randomUUID().toString());
    chain.doFilter(req, res);
  }
}
