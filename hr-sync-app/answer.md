# Respostas — HR Sync App

## Backend
1. Configurar `RestTemplateBuilder` com `setConnectTimeout`/`setReadTimeout` (ex.: 2s), coerente com o `TimeLimiter`.
2. Definir ordem explícita: `@CircuitBreaker` por fora, `@Retry` por dentro (ou usar `Resilience4j` combinando via `Decorators` programaticamente para controlar a ordem).
3. `private List<Employee> fallback(Throwable t) { return List.of(); }`.
4. Adicionar `@Bulkhead(name = "hrClient", type = Bulkhead.Type.THREADPOOL)`.
5. `wait-duration: 500ms` + `enable-randomized-wait: true` (jitter) ou backoff exponencial.
6. Aumentar `sliding-window-size` (ex.: 20) e ajustar `minimum-number-of-calls`.
7. Acrescentar `?sslmode=require` à URL JDBC.
8. Mover para Azure Key Vault + Managed Identity (`spring-cloud-azure-starter-keyvault`), nunca no `application.yml`.

## Frontend
9. Nunca colocar chaves reais no `environment.ts` — usar um proxy/backend a intermediar chamadas a serviços do Azure que exigem chave.
10. `this.http.post(...).pipe(timeout(5000))`.
11. `.pipe(finalize(() => (this.loading = false)))`.
12. Trocar `setInterval` manual por `interval(3000).pipe(switchMap(...))` ou `retry({ count: 3, delay: 1000 })`.

## K8s / Azure
13. `az aks update --attach-acr <registry>` (remove a necessidade de `imagePullSecrets`).
14. Ativar o CSI Secret Store driver do Key Vault e montar o segredo via `SecretProviderClass`.
15. Ativar o Cluster Autoscaler no node pool (`az aks nodepool update --enable-cluster-autoscaler`).
