# Respostas — Announcements App

## Backend / Migrações
1. Tirar `ddl-auto: update` (usar `validate`, deixando o Flyway ser a única fonte da verdade do schema).
2. Renomear `V1__seed_data.sql` para `V2__seed_data.sql` (migrations aplicadas nunca se editam nem repetem versão).
3. Atualizar a entidade: `@Column(name = "content") private String body;` (ou renomear o campo Java para `content`).
4. `auditorProvider()`: ler `SecurityContextHolder.getContext().getAuthentication().getName()`, com fallback só quando não autenticado.
5. `PageRequest.of(page - 1, size)`.
6. `PageRequest.of(page - 1, size, Sort.by("createdAt").descending())`.
7. Trocar `Page<Announcement>` por `Slice<Announcement>` no endpoint de "carregar mais" (sem `COUNT`); manter `Page` só onde o total é mesmo necessário (ex.: mostrar "página X de Y").

## Frontend
8. Ajustar `page` inicial e incrementos depois de corrigir o backend (mantém os dois lados consistentes — ou 0-indexado nos dois, ou 1-indexado nos dois, nunca misturado).
9. `Math.ceil(this.totalItems / this.pageSize)`.
10. Chave de cache composta: `cache[filtro + ':' + page]`, e limpar o cache sempre que o filtro mudar.
11. Nunca pedir `size` grande para "ter tudo" — paginar de verdade, ou usar scroll infinito com páginas pequenas.

## K8s
12. Usar um `Job`/`initContainer` dedicado a `flyway migrate`, que corre uma vez antes do rollout da app (ou usar `spring.flyway.enabled=false` nos pods da app + um passo de CI/CD que corre as migrations antes do deploy).
13. Ver ponto 12.
14. Só fazer deploy de migrations backward-compatible com o código anterior (ex.: em vez de `RENAME COLUMN`, fazer "adicionar nova coluna → app escreve nas duas → remover a antiga numa migration seguinte").
