# JSTL


Constrói views sem lógica Java embutida.

## Implementa

- JSTL core: `c:if`, `c:choose`, `c:forEach`, `c:url`.
- formatação e i18n.
- fragments JSP para layout simples.
- escaping de output para impedir XSS refletido/armazenado.
- paginação e filtros renderizados no servidor.
- visualização condicional baseada em permissões, deixando claro que esconder UI **não é autorização**.

## Exercício de segurança

Persiste `<script>alert(1)</script>` como descrição de pedido num teste controlado. Garante que a página mostra texto e não executa HTML arbitrário.

## Defesa

Explica Expression Language, scopes (`request`, `session`, `application`) e por que scriptlets devem ser evitados.
