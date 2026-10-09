# Spring Debugging Checklist

1. A request chegou à aplicação?
2. Passou pelos filters/security?
3. Qual handler foi selecionado?
4. Binding/conversion/validation falhou?
5. Entrou no transaction boundary esperado?
6. O bean é o bean/proxy que pensas que é?
7. Houve self-invocation?
8. Quantas queries foram executadas?
9. A pool de threads/conexões está saturada?
10. O timeout é teu ou de downstream?
11. A exception foi traduzida/engolida por algum layer?
12. Logs têm correlation-id/trace-id?
13. Métrica confirma o problema ou só o log parece suspeito?
