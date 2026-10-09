# Java Collections & Arrays Mastery Curriculum

Ambiente prático focado em trade-offs de baixo nível, complexidade assintótica e Stream API no Java 17+.

## Estrutura dos Módulos

1. **`01-arrays-primitive`**: Inversão in-place de arrays primitivos usando dois ponteiros ($O(1)$ de memória auxiliar).
2. **`02-list-performance`**: Comparativo prático de desempenho entre `ArrayList` e `LinkedList` (`append`, `remove(0)`, `get(i)`).
3. **`03-stream-api`**: Pipeline funcional e agregações avançadas utilizando `Collectors.groupingBy` e `collectingAndThen`.
4. **`04-sorting-searching`**: Implementação de busca binária genérica com `Comparator`, validando o contrato `RandomAccess`.

## Como Executar

Execute o script de configuração a partir da raiz:

```bash
chmod +x scripts/*.sh
./scripts/setup.sh
```

Para rodar o benchmark de listas:

```bash
./scripts/benchmark.sh
```
