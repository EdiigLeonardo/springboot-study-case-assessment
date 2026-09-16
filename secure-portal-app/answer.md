# Respostas — Secure Portal

## Backend
1. Trocar `NoOpPasswordEncoder` por `BCryptPasswordEncoder`; regravar o seed com hash real.
2. Chave JWT: `Keys.hmacShaKeyFor(...)` a partir de uma variável de ambiente com ≥256 bits aleatórios (nunca no código).
3. `extractEmail`: apanhar `ExpiredJwtException`/`JwtException` explicitamente e devolver "não autenticado" de forma clara (não engolir tudo em silêncio).
4. Adicionar `@EnableMethodSecurity` na configuração.
5. Só desligar CSRF se a app for 100% stateless (sem cookies de sessão); caso contrário, manter e integrar o token CSRF no frontend.
6. Remover `sessionFixation(...::none)` — deixar o valor por omissão (`migrateSession`/`changeSessionId`).
7. `addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)`.
8. Validar `redirect` contra uma whitelist de domínios/paths internos antes de redirecionar.

## Frontend
9. Guardar o token em cookie `httpOnly` (setado pelo backend) em vez de `localStorage`.
10. Ouvir `window.addEventListener('storage', ...)` para propagar logout entre separadores.
11. Decodificar o `exp` do JWT no guard e comparar com `Date.now()`.
12. Enviar o cabeçalho `X-XSRF-TOKEN` lido do cookie `XSRF-TOKEN` (Angular já suporta isto com `withXsrfConfiguration`).

## K8s
13. `automountServiceAccountToken: false` no `spec` do Pod/Deployment.
14. Montar o Secret como volume (`volumeMounts` + `volumes.secret`) em vez de `env`.
15. Criar uma `NetworkPolicy` deny-all por omissão + regra explícita a permitir só `portal-backend → postgres`.
