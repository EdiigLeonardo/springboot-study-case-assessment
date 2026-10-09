# Spring Forms


Evolui o Portal para criação/edição de pedidos com **Spring form binding**.

## Implementa

- Form backing object separado da entidade JPA.
- `<form:form>`, `<form:input>`, `<form:select>`, `<form:checkbox>`, `<form:hidden>`.
- Binding de nested objects.
- Conversão de `String -> Money/Currency` com `Converter` ou `Formatter`.
- `@InitBinder` para uma regra local e comparação com conversores globais.
- Proteção contra over-posting: demonstra por que nunca deves bindar uma entidade inteira sem controlo.
- Edit form com optimistic version field.

## Experimento obrigatório

Adiciona ao request um campo `role=ADMIN` que não pertence ao formulário permitido. Demonstra por teste que ele não consegue alterar autoridade do utilizador.

## Conclusão

Explica a diferença entre binding, conversion, formatting e validation; e quando o `WebDataBinder` entra no pipeline.
