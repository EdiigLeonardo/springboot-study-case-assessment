# Guia — Request Pipeline App (ronda 10)

Padrão: Filters + HandlerInterceptors (cadeia de processamento de pedidos). Só backend, sem BD, de propósito — o foco é o ciclo de vida do pedido em si.

## Bugs (9)
- [ ] `RequestContext`: `ThreadLocal` sem `remove()` → contamina pedidos seguintes na mesma thread pooled
- [ ] `UserContextFilter`: lê `SecurityContextHolder` mas corre antes da cadeia do Spring Security estar autenticada
- [ ] `CorrelationIdFilter`: `MDC.put` sem `MDC.clear()` no fim
- [ ] `AuditInterceptor.preHandle`: devolve `false` sem escrever status/corpo → cliente recebe 200 vazio sem explicação
- [ ] `AuditInterceptor.preHandle`: `Integer.parseInt` sem try/catch → um componente "só de auditoria" derruba pedidos válidos
- [ ] `WebConfig`: `addPathPatterns("/api/admin")` sem `/**` → sub-rotas escapam à auditoria
- [ ] `BodyLoggingInterceptor`: lê `getInputStream()` diretamente → `@RequestBody` do controller chega vazio
- [ ] `SecurityConfig`: nenhum `@Order`/`setOrder()` nos filtros próprios → ordem entre eles é indeterminada
- [ ] `AsyncController.slow()` + interceptors: `afterCompletion` (se implementado) dispararia antes do `CompletableFuture` terminar de verdade

## Features para dominares
- Implementar `afterCompletion` correto num interceptor, contemplando pedidos assíncronos (`CallableProcessingInterceptor`/`DeferredResult`).
- Trocar a leitura direta do `InputStream` por `ContentCachingRequestWrapper` (permite ler o corpo sem o "gastar").
- Configurar `@Order`/`FilterRegistrationBean.setOrder()` explicitamente para os 2 filtros próprios, e documentar a ordem esperada face à cadeia do Spring Security.
- Adicionar testes que arrancam 2 pedidos em threads diferentes do mesmo pool e provam que `RequestContext`/MDC não vazam entre eles.

Soluções: `answer.md`.
