# Exercício 01 — Motor de encomendas sem framework

Implemente um domínio de encomendas totalmente em Java puro.

## Requisitos
- `Money` imutável com moeda e escala correta.
- `Product`, `OrderLine`, `Order`, `Customer`.
- Estados: DRAFT, CONFIRMED, PAID, CANCELLED.
- Transições inválidas devem falhar com exceptions de domínio.
- Descontos via Strategy, sem `if` gigante.
- Repositório em memória com `Map` e sem expor mutabilidade interna.
- Relatório usando Streams: receita por cliente, top produtos, ticket médio.
- Use generics onde fizer sentido.
- Defina corretamente `equals/hashCode`.

## Bugs que você deve evitar
- BigDecimal construído com double.
- `Optional` em campos de entidade.
- retornar `List` mutável interna.
- usar exception genérica para regras de domínio.

## Definition of Done
- 20+ testes unitários.
- nenhuma classe de domínio depende de Spring.
- todas as invariantes estão descritas em testes.
