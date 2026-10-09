# Capstone — OrderFlow Enterprise

Não é um projeto novo: combina o que construíste nos quatro labs num sistema coerente.

## Cenário

Uma empresa B2B recebe encomendas por portal web. Gestores aprovam pedidos, pagamentos chegam por ficheiros externos, e alguns dados de cliente vivem noutro serviço.

## Requisitos

### Web
- MVC server-side para operações internas.
- Forms + validation + i18n.
- páginas JSP/JSTL seguras.

### Data
- transações corretas.
- concorrência na aprovação.
- paginação eficiente.
- zero N+1 nas páginas críticas.

### Security
- autorização por ownership + permission.
- proteção CSRF.
- auditoria.

### Batch
- reconciliação diária idempotente e reiniciável.

### Cloud
- customer-service remoto.
- gateway.
- timeout/circuit breaker/tracing.

### Advanced
- event after commit.
- caching controlado.
- configuration properties.
- métricas e health.

## Incident exam

Resolve sem dicas:

1. Dois managers aprovam o mesmo pedido simultaneamente.
2. Um deployment introduz N+1 e p95 passa de 150ms para 2.8s.
3. customer-service fica lento e começa a consumir todas as threads do order-service.
4. Um utilizador altera o ID no URL e tenta aceder ao pedido de outro cliente.
5. O batch é reiniciado após crash e não pode duplicar reconciliações.
6. Um listener envia email antes do commit; depois a transação faz rollback.
7. Um método `@Transactional` parece ignorar a annotation quando chamado internamente.
8. Uma configuração errada chega a produção e deixa timeout em 30 segundos.

## Exame de conclusão

Entrega:

- diagrama de arquitetura;
- sequence diagram de uma criação de pedido;
- threat model curto;
- ADR sobre transações;
- ADR sobre comunicação síncrona;
- dashboard mínimo com métricas;
- 10 integration tests de alto valor;
- postmortem de um dos incidentes.

Só concluas o laboratório quando conseguires defender tudo sem depender de nomes de annotations como resposta.
