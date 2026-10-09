# Spring advanced concepts


Abre a caixa preta do Spring.

## Parte A — IoC container

- cria beans por `@Bean`, stereotype e import.
- usa `BeanPostProcessor` de forma controlada.
- observa lifecycle callbacks.
- demonstra scopes.
- provoca circular dependency e corrige design.

## Parte B — Proxies e AOP

- cria uma annotation `@Measured` e aspect.
- demonstra proxy boundaries.
- reproduz self-invocation quebrando `@Transactional` ou `@Async`.
- distingue JDK proxy e class-based proxy conceitualmente.

## Parte C — Events

- domain/application event.
- `@TransactionalEventListener` after commit.
- explica por que evento in-process não é um message broker.

## Parte D — Infraestrutura

- `@ConfigurationProperties` tipado + validation.
- cache com invalidação explícita.
- scheduling com proteção contra concorrência entre instâncias.
- `@Async` com executor configurado, bounded queue e tratamento de exceptions.
- Actuator, Micrometer e tracing.

## Parte E — Auto-configuration

Cria um mini starter `request-audit-spring-boot-starter`:

- auto-configuração condicional;
- properties;
- bean default substituível pelo consumidor;
- teste de contexto.

## Parte F — Performance

Diagnostica:

- thread pool esgotado;
- connection pool esgotado;
- endpoint lento por N+1;
- cache stampede;
- heap pressure;
- bloqueio dentro de `@Async`.

## Defesa final

Explica como Spring encontra, instancia, modifica e proxifica um bean do scan até ficar pronto para uso.
