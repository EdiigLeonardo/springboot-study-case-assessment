# Payments Interview Lab — Java / Spring / Angular

A deliberately flawed, production-style payments application designed for interview preparation and hands-on debugging.

## Scenario

You joined **AcmePay**, a payment platform used by merchants in Portugal and France. The system has:

- a Spring Boot REST backend;
- an Angular web application;
- a scheduled batch for settlement/reconciliation;
- concurrency-sensitive balance and payment processing;
- database transactions;
- asynchronous jobs;
- reusable UI validation for card data;
- tests that expose only part of the defects.

The code contains intentional bugs, anti-patterns, race conditions, design smells, observability problems, and performance traps. Some bugs are obvious; others require understanding framework behavior.

## Goal

Work as if this were a real support/evolution project:

1. Run or inspect the tests.
2. Reproduce bugs.
3. Form a hypothesis before changing code.
4. Fix one problem at a time.
5. Add or improve tests before/after each fix.
6. Explain *why* the bug exists and *which framework concept* is involved.
7. Record trade-offs: correctness, performance, maintainability, observability.

## Suggested order

- Phase 1 — Java language & design: `docs/01-java-core-challenges.md`
- Phase 2 — Spring & transactions: `docs/02-spring-challenges.md`
- Phase 3 — Threads, concurrency & performance: `docs/03-concurrency-challenges.md`
- Phase 4 — Angular & RxJS: `docs/04-angular-challenges.md`
- Phase 5 — Batch & production support: `docs/05-batch-production-challenges.md`
- Phase 6 — Interview simulation: `docs/06-interview-questions.md`

Do **not** start with `docs/99-solution-map.md` unless you are blocked; it contains spoiler-level hints.

## Architecture

```text
frontend (Angular)
   |
   v
backend REST (Spring Boot)
   |-- payment service
   |-- user service
   |-- transaction boundaries
   |-- async notifications
   |
   +--> H2 DB
   |
   +--> batch settlement/reconciliation
```

## Intentional learning topics covered

Java: SOLID, lambdas/streams, collections, immutability, equals/hashCode, exceptions, logging, threads, synchronization, thread pools, race conditions, performance, deduplication.

Spring: IoC/DI, beans, singleton scope, stereotype annotations, `@Configuration`, `@Bean`, `@Transactional`, propagation/rollback, proxies/self-invocation, `@Async`, scheduled jobs, REST, validation, exception handling.

Angular: components, services, reactive forms, custom validators, directives, reusable card masking, RxJS Observables, Promise vs Observable, cancellation, subscription leaks, `async` pipe, interceptors, Material, styles, change detection, error handling.

Batch/support: idempotency, chunking, N+1, lock contention, retries, partial failures, logging, correlation IDs, metrics, duplicate processing.

## Important

This project is intentionally incorrect. Never copy its flawed patterns into production before completing the exercises.

## Run locally

Backend (Java 17 + Maven):

```bash
cd backend
mvn spring-boot:run
```

Frontend (Node/npm + Angular CLI through npm scripts):

```bash
cd frontend
npm install
npm start
```

The frontend dev server proxies `/api` to `http://localhost:8080`.

Useful seeded accounts:

- `PT50000000000000000000001` — 10000.00
- `PT50000000000000000000002` — 2500.00
- `FR7600000000000000000000001` — 5000.00

Example create-payment payload (the card field is intentionally frontend-only in this lab):

```json
{
  "externalReference": "ORDER-1001",
  "merchantId": "MERCHANT-PT-1",
  "payerIban": "PT50000000000000000000001",
  "payeeIban": "PT50000000000000000000002",
  "amount": 125.50
}
```

## Difficulty model

The repository mixes several defect classes on purpose:

- **Level A — visible smells:** logging, hard-coded branches, CSS coupling.
- **Level B — framework traps:** proxies, singleton state, stale observables, form/value-accessor behavior.
- **Level C — production bugs:** idempotency races, lost updates, poison batch items, partial remote side effects.
- **Level D — scaling/distributed reasoning:** multi-instance scheduling, JVM locks vs DB coordination, executor backpressure, batch resumability.

A strong interview answer should connect all four levels: code, framework behavior, production consequence, and test/monitoring strategy.
