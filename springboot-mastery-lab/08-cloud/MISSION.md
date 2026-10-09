# Spring Cloud


Cria `cloud-system` a partir de uma parte do Portal.

Serviços mínimos:

- `order-service`
- `customer-service`
- `api-gateway`

## Implementa

- configuração externa e profiles.
- Gateway com routing e correlation-id propagation.
- cliente HTTP declarativo ou `RestClient`/HTTP Service Client, justificando escolha.
- connect/read timeouts explícitos.
- circuit breaker.
- retry **somente** para operações seguras/idempotentes.
- fallback que não mente sobre sucesso.
- health/readiness endpoints.
- distributed tracing/OpenTelemetry.
- contract test entre serviços.

## Opcional avançado

- Spring Cloud Config.
- service discovery quando conseguires justificar a necessidade num ambiente onde DNS/Kubernetes não chega.

## Chaos drills

1. customer-service demora 15s.
2. customer-service responde 500 em 50% das chamadas.
3. gateway recebe uma request sem correlation-id.
4. configuração muda de forma incompatível.
5. downstream processou a operação mas a resposta perdeu-se.

## Defesa

Explica por que microservices adicionam complexidade; timeout budget, circuit breaker state machine, retries storms, idempotência e observabilidade distribuída.
