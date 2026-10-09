# CAPSTONE — Commerce Ledger Platform

Construa um backend para marketplace com encomendas, stock, pagamento e faturação.

## Funcionais
- criar cliente/produto;
- reservar stock;
- criar e confirmar encomenda;
- autorizar pagamento;
- emitir evento `OrderPaid`;
- gerar ledger financeiro;
- consultar histórico paginado.

## Não funcionais
- multi-tenant;
- idempotência;
- segurança por ownership/role;
- PostgreSQL + migrations;
- Kafka + outbox;
- retries seguros;
- observabilidade;
- Testcontainers;
- API versionada;
- zero perda de dinheiro/stock em concorrência.

## Restrições deliberadas
1. Payment provider às vezes responde depois de 8s.
2. Cliente pode repetir a mesma requisição 5 vezes.
3. Kafka pode entregar duplicado.
4. Duas instâncias da aplicação processam o mesmo tipo de trabalho.
5. O DB fica lento durante 3 minutos.
6. Um deploy introduz payload de evento v2 enquanto consumers v1 ainda existem.

## Incident drills
Resolva e documente:
- stock negativo;
- pagamento duplicado;
- N+1 que derruba o endpoint;
- token de tenant A acessa recurso de tenant B;
- fila cresce sem limite;
- p99 passa de 2s;
- migration incompatível durante rolling deploy.

## Entregáveis
- código;
- testes;
- ADRs;
- threat model;
- runbook de incidentes;
- diagrama C4 simples;
- relatório de performance;
- defesa final.
