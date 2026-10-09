# Exercício 05 — JPA sem magia

Modele contas, transferências e ledger.

## Requisitos
- PostgreSQL + Flyway.
- Ledger append-only.
- Transferência atómica.
- optimistic locking e teste de lost update.
- uma query com paginação eficiente.
- reproduza N+1 e depois corrija.
- compare fetch join, entity graph e projeção.
- crie índices e use EXPLAIN ANALYZE.
- demonstre rollback e propagation.

## Incidente
Duas requisições tentam gastar o mesmo saldo ao mesmo tempo. O sistema nunca pode criar dinheiro nem saldo inválido.
