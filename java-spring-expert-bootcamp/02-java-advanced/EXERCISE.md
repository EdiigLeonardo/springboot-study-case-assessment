# Exercício 02 — Runtime, IO e concorrência moderna

Construa um importador de ficheiros CSV gigantes que valida registos e calcula agregados.

## Requisitos
- Streaming IO: não carregar o ficheiro inteiro em memória.
- Pipeline com virtual threads para tarefas bloqueantes.
- Limite explícito de concorrência para proteger dependências.
- `record` para DTOs e `sealed interface` para resultados.
- Benchmark simples: sequencial vs concorrente.
- Simule 1 milhão de linhas.
- Faça heap dump / JFR opcional e escreva suas observações.
- Use reflection apenas num pequeno serializador didático e explique custo/risco.

## Incidentes injetados
- file descriptor leak;
- shared mutable accumulator;
- unbounded task creation;
- swallowed interruption.

Você deve encontrar e corrigir os quatro.
