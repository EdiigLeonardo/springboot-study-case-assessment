# Exercício 04 — API de pagamentos robusta

Implemente `/api/v1/payments`.

## Obrigatório
- request/response DTOs distintos do domínio;
- Bean Validation;
- Problem Details (`application/problem+json`);
- idempotency-key em POST;
- paginação e filtros;
- optimistic concurrency com ETag/If-Match OU versão explícita;
- timeout consistente;
- OpenAPI;
- nenhuma stack trace exposta ao cliente;
- logs com correlation id.

## Cenários de teste
- POST duplicado com mesma chave;
- payload inválido;
- recurso inexistente;
- atualização concorrente;
- retry do cliente após timeout.
