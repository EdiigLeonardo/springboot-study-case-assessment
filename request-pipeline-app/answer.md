# Respostas — Request Pipeline App

1. Implementar `HandlerInterceptor.afterCompletion` (ou um `Filter` com `try/finally`) a chamar `RequestContext.remove()` sempre, mesmo em erro.
2. Registar `UserContextFilter` depois do filtro de autenticação do Spring Security (`addFilterAfter` numa `SecurityFilterChain` customizada), ou lê o utilizador dentro de um interceptor (que já corre depois da cadeia de segurança) em vez de um `Filter` cru.
3. `try/finally { MDC.clear(); }` a envolver o `chain.doFilter(...)`.
4. Quando `X-Audit-Token` falta: `res.setStatus(401); res.getWriter().write("Falta X-Audit-Token"); return false;`.
5. `try { priority = Integer.parseInt(...); } catch (NumberFormatException e) { /* default ou 400 */ }`.
6. `addPathPatterns("/api/admin/**")`.
7. Usar `ContentCachingRequestWrapper` para poder logar o corpo e ainda deixá-lo disponível para o controller.
8. Definir `@Order(1)` no `CorrelationIdFilter` e `@Order(2)` no `UserContextFilter` (ou registar via `FilterRegistrationBean` com `setOrder`).
9. Usar `CallableProcessingInterceptor`/`DeferredResult` com os seus próprios callbacks (`postProcess`, `afterCompletion` específicos de async) em vez de assumir que o pedido acabou quando o método controller "retorna".
