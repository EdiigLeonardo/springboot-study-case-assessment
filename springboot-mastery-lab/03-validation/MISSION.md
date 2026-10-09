# Forms Validation


Adiciona validação em três níveis.

## Nível 1 — Bean Validation

- `@NotBlank`, `@Size`, `@Positive`, constraints de datas.
- mensagens internacionalizadas.

## Nível 2 — Constraint customizada

Cria `@ValidDeliveryWindow` para garantir coerência entre datas.

## Nível 3 — Regras de negócio

Validação que necessita de repositório/serviço, por exemplo limite de crédito. Não escondas isso dentro de uma annotation sem discutir o trade-off.

## Implementa ainda

- `BindingResult` imediatamente após o objeto validado.
- field errors e global errors no JSP.
- validation groups para create vs update.
- testes unitários do validator e `MockMvc` da UX de erros.

## Bug drill

Troca a posição de `BindingResult` e explica o resultado. Submete números inválidos e distingue `typeMismatch` de uma constraint violada.
