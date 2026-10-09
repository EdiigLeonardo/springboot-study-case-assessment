# Spring e acesso a base de dados


Transforma o Portal numa aplicação transacional real.

## Implementa

- PostgreSQL + migrations.
- aggregate `Order` com linhas e estado.
- Spring Data repositories com queries derivadas e `@Query`.
- paginação e sorting.
- DTO projections para uma listagem eficiente.
- `@Transactional` na camada de serviço.
- optimistic locking com `@Version`.
- uma query pessimistic lock para um caso justificado.
- tratamento de N+1 com fetch strategy explícita.
- auditoria `createdAt/updatedAt`.
- Testcontainers para integration tests.

## Experimentos obrigatórios

1. Mostra `LazyInitializationException` num cenário controlado e corrige a causa, não o sintoma.
2. Cria uma transação que chama um método `@Transactional` da mesma classe e explica self-invocation/proxy.
3. Provoca duas atualizações concorrentes e observa optimistic locking.
4. Mede número de queries antes/depois da correção do N+1.

## Defesa

Explica persistence context, dirty checking, flush, transaction boundaries, isolation, propagation e rollback semantics.
