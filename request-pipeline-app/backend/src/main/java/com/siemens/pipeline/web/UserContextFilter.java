package com.siemens.pipeline.web;
import jakarta.servlet.*;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.io.IOException;

// BUG (ordem filtros vs Spring Security): este filtro tenta ler o
// utilizador autenticado, mas corre ANTES do filtro de autenticacao do
// Spring Security (sem @Order/sem estar depois dele) -> SecurityContext
// ainda vazio aqui -> RequestContext fica sempre "anonimo".
@Component
public class UserContextFilter implements Filter {
  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    RequestContext.set(auth != null ? auth.getName() : "anonimo");
    chain.doFilter(req, res); // RequestContext.get() nunca e limpo no fim (ver RequestContext.java)
  }
}
