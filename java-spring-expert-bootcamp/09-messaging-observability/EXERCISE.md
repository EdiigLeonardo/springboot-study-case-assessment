# Exercício 09 — Eventos, retries e observabilidade

Converta o pagamento em fluxo assíncrono.

## Requisitos
- Kafka producer/consumer;
- at-least-once delivery assumida;
- consumer idempotente;
- retry com backoff;
- DLQ;
- transactional outbox;
- correlation/trace id atravessando HTTP e mensagem;
- Micrometer metrics;
- health/readiness;
- dashboard conceitual com RED metrics.

## Chaos cases
- consumer cai depois do side effect e antes do commit do offset;
- broker indisponível;
- mensagem duplicada;
- mensagem fora de ordem;
- payload incompatível.
