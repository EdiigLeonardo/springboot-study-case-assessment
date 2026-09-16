# Guia — HR Sync App (ronda 9: Resilience4j + Azure)

Padrão: Resilience (Circuit Breaker/Retry/Timeout, Resilience4j). Domínio: sincronização de funcionários a partir de um sistema HR externo.

## Backend (8)
- [ ] `HrClient`: `RestTemplate` sem `connectTimeout`/`readTimeout` → o `TimeLimiter` do yml nunca corta a chamada real
- [ ] `@Retry` "por fora" do `@CircuitBreaker` sem ordem definida → cada retry conta como falha extra para o circuito (ou o inverso)
- [ ] `fallback()`: assinatura não bate certo com `fetchEmployees()` (falta `Throwable`)
- [ ] `EmployeeSyncService`/`HrClient`: sem `@Bulkhead` → uma chamada lenta esgota todas as threads da app
- [ ] `retry.wait-duration: 100ms` sem backoff/jitter → 5 tentativas quase instantâneas (thundering herd)
- [ ] `circuitbreaker.sliding-window-size: 3` → circuito instável com pouco tráfego
- [ ] Ligação ao Postgres do Azure sem `sslmode=require`
- [ ] `hr.external-api-key` escrita em texto no `application.yml` (devia vir do Key Vault/Managed Identity)

## Frontend (4)
- [ ] `environments.example.ts`: chave do Azure Maps no bundle (tudo no frontend é público)
- [ ] `SyncService.trigger()`: sem `timeout()` → pedido pendente para sempre se o backend prender
- [ ] `SyncPanelComponent.run()`: sem `finalize()` → spinner preso em caso de erro
- [ ] `setInterval` a fazer polling manual em vez de operadores RxJS (`retry`/`timer`)

## K8s / Azure (3)
- [ ] `imagePullSecrets` com credencial estática em vez da integração nativa AKS↔ACR
- [ ] Segredo do Azure metido num `Secret` do Kubernetes em vez do CSI driver do Key Vault
- [ ] HPA a escalar até 20 réplicas sem Cluster Autoscaler → pods presos em `Pending`

## Features para dominares
**Spring:** configurar `@Bulkhead` + ordem explícita dos aspectos (`@Retry` dentro do `@CircuitBreaker`); métricas do Resilience4j no Actuator.
**Docker:** timeout do `RestTemplate`/`WebClient` alinhado com o `TimeLimiter`.
**PostgreSQL/Azure:** ligação com `sslmode=require` + Managed Identity em vez de password.
**Angular:** wrapper de `HttpClient` com `timeout()` + `retry({ delay })` com backoff.
**Azure/K8s:** `az aks update --attach-acr`; CSI Secret Store driver para o Key Vault; ativar Cluster Autoscaler.

Soluções: `answer.md`.
