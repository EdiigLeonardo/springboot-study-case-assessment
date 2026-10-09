# Spring Security


Protege o Portal como uma aplicação empresarial.

## Implementa

- `SecurityFilterChain` explícita.
- login com utilizadores persistidos.
- password hashing forte.
- roles `USER`, `MANAGER`, `ADMIN` e authorities granulares.
- authorization por rota e `@PreAuthorize` no serviço.
- CSRF ativo nos forms.
- session fixation protection e logout.
- remember-me apenas se conseguires explicar risco e implementação.
- custom access denied / authentication failure UX.
- audit events para login e ações administrativas.

## Attack lab

Cria testes para:

- CSRF ausente;
- IDOR/BOLA: user A tenta abrir pedido de user B;
- privilege escalation por parâmetro de form;
- endpoint esquecido sem autorização;
- brute-force/rate limiting: desenha a defesa mesmo que a implementação seja externa ao Spring Security.

## Defesa

Desenha a filter chain e explica Authentication, Principal, SecurityContext, AuthenticationProvider, UserDetailsService e AuthorizationManager.
