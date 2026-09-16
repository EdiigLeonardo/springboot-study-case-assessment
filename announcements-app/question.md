# Guia — Announcements App (ronda 8: paginação + migrações)

Padrão: Spring Data JPA Auditing (`@CreatedDate`/`@CreatedBy`). Domínio: anúncios internos.

## Backend / Migrações (9)
- [ ] `ddl-auto: update` ligado ao mesmo tempo que o Flyway
- [ ] `V1__init.sql` e `V1__seed_data.sql` com a mesma versão
- [ ] `V3__rename_body_to_content.sql` renomeia a coluna, mas `Announcement.body` continua `@Column(name="body")`
- [ ] `auditorProvider()` devolve sempre `"system"` (ignora quem está autenticado)
- [ ] Controller: `page` do frontend (1-indexado) passado direto a `PageRequest.of()` (0-indexado) → salta a 1ª página
- [ ] `PageRequest.of(page, size)` sem `Sort` → ordem não determinística entre pedidos
- [ ] Sempre `Page<>` (faz `COUNT(*)`) mesmo quando só precisas de "carregar mais"
- [ ] (revê o resto do controller à procura de mais 1 problema de paginação)
- [ ] (revê o `AnnouncementRepository`/entidade à procura de mais 1 problema)

## Frontend (4)
- [ ] `page = 1` como estado inicial, alinhado com o bug off-by-one do backend
- [ ] `totalPages`: `Math.floor` em vez de `Math.ceil` → última página parcial desaparece
- [ ] `cache` por número de página, sem chave de filtro → mostra dados errados se filtrares
- [ ] Serviço preparado para pedir `size` grande em vez de paginar de verdade (repara no comentário)

## K8s (3)
- [ ] 3 réplicas correm o Flyway em simultâneo no arranque → corrida a aplicar a mesma migration
- [ ] Sem `initContainer`/`Job` a isolar as migrations do arranque da app
- [ ] `maxUnavailable: 0` mantém pods com código antigo a servir tráfego durante uma migration que renomeia coluna (V3) → quebra a meio do rollout

## Features para dominares
**Spring:** paginação por cursor/keyset (evita o penhasco de performance do `OFFSET` grande); `AuditorAware` real ligado ao `SecurityContext`.
**PostgreSQL:** `EXPLAIN ANALYZE` numa query com `OFFSET 50000` vs. keyset, para veres a diferença.
**Docker/K8s:** `initContainer` a correr `flyway migrate` antes do container principal arrancar.
**Angular:** paginação com estado sincronizado na URL + cache invalidado por chave `(filtro, página)`.

Soluções: `answer.md`.
