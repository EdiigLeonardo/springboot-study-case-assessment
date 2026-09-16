# Guia — Secure Portal (ronda 7, foco Spring Security)

Padrão: autenticação stateless com JWT (filtro custom + Spring Security). Domínio mínimo (login + endpoint admin) — o objetivo é a cadeia de segurança, não o CRUD.

## Backend / Security (8)
- [ ] Seed com password `{noop}` + `NoOpPasswordEncoder` → passwords em texto puro
- [ ] `JwtService`: chave HMAC curta e fixa no código
- [ ] `JwtService.extractEmail`: engole qualquer exceção (incluindo token expirado) → trata como válido/anónimo sem distinguir
- [ ] `SecurityConfig`: falta `@EnableMethodSecurity` → `@PreAuthorize` no `AdminController` nunca é avaliado
- [ ] `csrf().disable()` global numa app que também usa sessão
- [ ] `sessionFixation(...::none)` → sem proteção contra session fixation
- [ ] `addFilterAfter(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)` → ordem errada do filtro
- [ ] `AuthController.callback`: redirect aberto (`redirect` sem whitelist/validação)

## Frontend (4)
- [ ] `AuthService.login`: token guardado em `localStorage` (exposto a XSS)
- [ ] `logout()`: sem sincronização entre separadores (evento `storage`)
- [ ] `authGuard`: só verifica presença do token, nunca a expiração
- [ ] `LoginComponent.doAdminAction`: POST sem cabeçalho CSRF

## K8s (3)
- [ ] Sem `automountServiceAccountToken: false`
- [ ] Secret injetado como variável de ambiente em vez de volume montado
- [ ] Sem `NetworkPolicy` nenhuma no namespace

## Features para dominares
**Spring:** refresh token + revogação (blacklist/rotação); rate limiting no `/login` (proteção contra brute force).
**Angular:** interceptor que troca automaticamente um token expirado por um novo (refresh flow).
**Docker:** `USER` não-root no Dockerfile do backend.
**PostgreSQL:** tabela de tentativas de login com índice por `email+created_at` para deteção de brute force.
**K8s:** `NetworkPolicy` a só permitir tráfego do backend para o Postgres (deny-all por defeito).

Soluções: `answer.md`.
