package com.siemens.pipeline.web;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuditInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {
    if (req.getHeader("X-Audit-Token") == null) {
      // BUG: devolve false (interrompe a cadeia) sem escrever status/corpo -
      // o cliente recebe um 200 vazio, sem pista nenhuma do porque falhou.
      return false;
    }
    // BUG: se isto lancar (ex.: parsing de um header mal formado), a
    // excecao nao e apanhada aqui - um componente "so de auditoria" derruba
    // o pedido inteiro, incluindo funcionalidade que nada tem a ver com auditoria.
    int priority = Integer.parseInt(req.getHeader("X-Priority"));
    req.setAttribute("priority", priority);
    return true;
  }
}
