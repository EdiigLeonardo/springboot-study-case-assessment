# Roadmap de Mestria

## Fase 1 — Spring Web clássico

Completa 01→06 dentro da aplicação `classic-web`.

### Resultado esperado
Consegues desenhar o fluxo HTTP completo:

`request -> servlet container -> filters -> DispatcherServlet -> HandlerMapping -> Controller -> service -> repository -> Model -> ViewResolver -> JSP -> response`

E consegues explicar onde entram binding, validation, exceptions, transactions e security.

## Fase 2 — Spring Batch

Constrói um job real com chunk processing, restart, retry, skip, métricas e idempotência.

## Fase 3 — Spring Cloud

Divide funcionalidades em serviços, introduz gateway, configuração externa, timeouts, circuit breaker e tracing.

## Fase 4 — Spring avançado

Abre a caixa preta: IoC container, lifecycle, proxies, AOP, events, transactions, caching, scheduling, async, observability, configuration properties e auto-configuration.

## Fase 5 — Capstone

Resolve incidentes e requisitos cruzados sem tutorial.

## Ordem obrigatória

01 MVC → 02 Forms → 03 Validation → 04 JSTL → 05 Data → 06 Security → 07 Batch → 08 Cloud → 09 Advanced → Capstone
