# Java + Spring Boot Expert Bootcamp

Um percurso compacto, cumulativo e orientado a trabalho real para sair de "sei usar" para "sei explicar, desenhar, testar, diagnosticar e otimizar" Java e Spring Boot.

## Stack do treino
- Java 25 LTS
- Maven 3.9+
- Spring Boot 4.1.1
- PostgreSQL
- Testcontainers
- Kafka (no laboratório de mensageria)
- Docker / Docker Compose

## Como usar
1. Faça os módulos em ordem.
2. Leia apenas `EXERCISE.md` antes de programar.
3. Rode os testes: `mvn test`.
4. Só depois consulte `HINTS.md`.
5. Ao terminar, responda ao `CHECKPOINT.md` sem IDE/Google.
6. Registe decisões em `DECISIONS.md`.

## Regra de ouro
Não avance porque "funcionou". Avance quando consegue explicar:
- por que funciona;
- quais invariantes existem;
- onde pode falhar;
- como testaria;
- qual o custo em memória/CPU/IO;
- que trade-offs fez.

## Percurso em 10 blocos
1. Java Core — modelação, collections, exceptions, generics, streams.
2. Java Advanced — JVM, memória, reflection, records/sealed, IO, virtual threads.
3. Spring Core — IoC/DI, lifecycle, proxies, AOP, configuration.
4. Spring Boot API — HTTP, validation, error model, idempotência, versionamento.
5. Data/JPA — transações, locking, N+1, índices, migrations, pagination.
6. Security — authn/authz, JWT, method security, CSRF/CORS, secrets.
7. Testing — unit, slice, integration, Testcontainers, contract, mutation-thinking.
8. Concurrency/Performance — races, locks, virtual threads, profiling, caching.
9. Messaging/Observability — Kafka, retries, DLQ, outbox, metrics, tracing.
10. Capstone — sistema real com requisitos contraditórios e incidentes.

## Critério para dizer "sei Java + Spring Boot"
Você deve conseguir completar o Capstone e defender oralmente as decisões listadas em `00-roadmap/FINAL-DEFENSE.md`.

## Comandos úteis
```bash
mvn -q test
mvn spring-boot:run
docker compose up -d
```
