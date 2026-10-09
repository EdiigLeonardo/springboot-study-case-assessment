# Spring Boot Mastery Lab

Um laboratório independente, focado exclusivamente em **Spring Framework / Spring Boot**.

## Objetivo

No fim, deves conseguir dizer "sei Spring Boot" porque consegues **construir, explicar, depurar e operar** aplicações Spring, e não apenas decorar annotations.

A progressão segue o programa solicitado:

1. MVC
2. Spring Forms
3. Forms Validation
4. JSTL
5. Acesso a base de dados
6. Spring Security
7. Spring Batch
8. Spring Cloud
9. Advanced concepts

## Filosofia

São poucos exercícios, mas cada um é deliberadamente profundo. O laboratório usa quatro aplicações:

- `classic-web`: MVC + JSP/JSTL + Forms + Validation + Data + Security.
- `batch-worker`: processamento batch idempotente e reiniciável.
- `cloud-system`: dois serviços + gateway + resiliência + configuração.
- `advanced-lab`: internals, eventos, AOP, cache, concorrência, observabilidade e performance.

O `capstone` combina tudo num cenário de produção.

## Stack-base

- Java 25 LTS
- Spring Boot 4.1.1
- Maven
- PostgreSQL
- Testcontainers
- Docker Compose

> O módulo JSP/JSTL existe de propósito para cobrir o currículo clássico de Spring MVC. Em sistemas modernos, Thymeleaf ou frontends separados são frequentes, mas aqui deves dominar o pipeline MVC tradicional antes de abstraí-lo.

## Como estudar

Para cada bloco:

1. Lê apenas `MISSION.md`.
2. Implementa sem procurar solução pronta.
3. Executa testes e critérios de aceitação.
4. Responde `DEFENSE.md` sem consultar documentação.
5. Só passas se conseguires explicar o *porquê* da solução.

## Regra de mestria

Um exercício só está concluído se conseguires:

- implementar;
- escrever testes;
- explicar o fluxo interno do Spring;
- diagnosticar uma versão quebrada;
- apontar trade-offs;
- explicar como operarias isso em produção.

Começa em `00-start-here/ROADMAP.md`.
