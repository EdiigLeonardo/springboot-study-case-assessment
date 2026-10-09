# Exercício 06 — Security: ataque e defesa

Proteja uma API multi-tenant.

## Requisitos
- login com password hash seguro;
- access + refresh token;
- rotação/revogação de refresh token;
- RBAC + regra por ownership;
- method security;
- CORS explícito;
- rate-limit conceitual/documentado;
- secrets fora do Git;
- testes de acesso cruzado entre tenants.

## Ataques a reproduzir
- IDOR/BOLA;
- privilege escalation;
- token expirado/revogado;
- brute force conceitual;
- mass assignment.

Escreva `THREAT-MODEL.md` com assets, trust boundaries, abuse cases e mitigations.
