# Architecture
Desenhe inicialmente um modular monolith. Só extraia serviço quando puder justificar boundary, ownership, consistência e custo operacional.

## Módulos sugeridos
- identity
- catalog
- inventory
- orders
- payments
- ledger
- messaging

## Regra
Dependências entre módulos precisam ser explícitas. Evite package `common` como depósito de abstrações sem dono.
