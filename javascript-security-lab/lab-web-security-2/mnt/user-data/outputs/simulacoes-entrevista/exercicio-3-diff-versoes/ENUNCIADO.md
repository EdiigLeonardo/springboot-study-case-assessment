# Exercício 3 — Review de diff (v2.3.0 → v2.4.0)

**Tempo: 25 minutos.**

A extensão "Tab Sessions" está na store há dois anos, com boas avaliações. A v2.3.0 passou por várias reviews sem problemas. Chegou agora a v2.4.0, submetida após uma mudança de proprietário da conta do programador.

Este é o cenário mais realista da tua futura função: **não estás a procurar código mau num vazio, estás a comparar duas versões e a perguntar-te se a mudança se justifica.**

Compara as duas pastas:

```bash
diff -ru v2.3.0 v2.4.0
```

Responde:
1. Que privilégios novos foram pedidos, e que funcionalidade nova os justificaria?
2. Há algum privilégio pedido que **não** seja usado por nenhuma funcionalidade visível? (é uma das perguntas mais reveladoras num review de extensões)
3. A v2.3.0 tinha duas verificações de segurança que desapareceram na v2.4.0. Quais?
4. Identifica a nova superfície de ataque criada por código que uma página web consegue alcançar.
5. Aprovarias esta atualização? O que pedirias ao autor?
