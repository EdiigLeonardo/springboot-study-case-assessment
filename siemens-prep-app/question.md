# Guia — Self-Service Portal (prep Siemens)

Stack: Java 21 + Spring Boot (hexagonal) + Angular 17 standalone + PostgreSQL/Flyway + Docker/K8s + GitHub Actions.

App: gestão de tickets (criar, listar, ver, mudar status, atribuir, comentar).

Estrutura backend (hexagonal):
```
domain/        modelo puro + ports (in/out) + exceptions
application/   use cases (implementam ports.in)
infrastructure/adapter/in/web/     controllers, dtos, mapper
infrastructure/adapter/out/persistence/  JPA, adapters
infrastructure/config/             security, swagger
```

Estrutura frontend:
```
core/            models, services, guard, interceptor
features/tickets/  list, detail, form (standalone components)
```

## Missão
Há ~20 problemas espalhados pelo projeto (bugs, más práticas, violações de arquitetura). Cobrem: Java, Spring Boot, hexagonal, Angular/RxJS, REST, segurança, Docker, K8s, CI/CD, SQL. Encontra-os e corrige-os. Categorias abaixo — sem localização exata, é o exercício.

- [X] Java: enum usado de forma frágil (ordinal)
- [X] Java: `equals()`/`hashCode()` incompleto
- [X] Spring: injeção por campo em vez de construtor
- [X] Spring: `@Transactional` sem efeito (self-invocation)
- [X] Spring: exception handler genérico a mais (mascara 400 como 500)
- [X] Hexagonal: adapter de entrada a falar direto com adapter de saída
- [X] Hexagonal: entidade JPA exposta na API
- [X] REST: verbo HTTP errado numa alteração de estado
- [X] REST/DTO: sem validação (`@NotBlank`, etc.)
- [X] Query: N+1 numa listagem
- [X] SQL: FK sem índice
- [X] Segurança: CORS demasiado aberto (`*` + credentials)
- [X] Segurança: sem controlo de role nos endpoints
- [X] Angular: subscribe sem `ngOnDestroy` (memory leak)
- [X] Angular: subscribe sem tratamento de erro
- [X] Angular: `*ngFor` sem `trackBy`
- [X] Angular: `snapshot.paramMap` em vez de observar mudanças de rota
- [X] Angular: form reativo sem `Validators`
- [X] Angular: interceptor não trata 401
- [X] Docker: imagem backend não é multi-stage
- [ ] Docker Compose: segredo em texto plano
- [ ] K8s: sem readiness/liveness probe
- [ ] K8s: password em ConfigMap em vez de Secret
- [ ] CI/CD: sem cache de dependências
- [ ] CI/CD: push da imagem sem depender dos testes

Ficheiro de soluções: `answer.md` — só consultar depois de tentar.