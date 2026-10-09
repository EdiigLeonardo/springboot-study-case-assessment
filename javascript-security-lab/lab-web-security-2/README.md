# Simulações do exercício técnico

Três exercícios que imitam o que te vão dar. Faz **um por dia**, cronometrado, e escreve a resposta antes de abrir o gabarito. Se leres os gabaritos primeiro, o material perde quase todo o valor.

| Exercício | Tempo | O que treina |
|---|---|---|
| 1 — Markdown Clipper | 20 min | reconhecimento de padrões perigosos; o 80/20 |
| 2 — PriceWatch | 40 min | fluxo de dados, cadeias de privilégio, capacidade vs comportamento |
| 3 — Diff de versões | 25 min | o cenário real da função: comparar versões de extensão confiável |

Gabaritos em `gabaritos/`. Todo o código é benigno — contém padrões inseguros para revisão, não malware funcional. Os domínios são fictícios.

---

## O 80/20: 15 padrões que cobrem quase tudo

Se só tiveres uma hora antes da entrevista, decora isto. Estes padrões apanham a grande maioria dos achados reais em extensões.

**Execução de código**
1. `eval(x)`, `new Function(x)`, `setTimeout("string")` — sempre achado se `x` não for literal no código.
2. Código ou configuração executável obtida por `fetch` — proibido em MV3, é *o* padrão de extensão que muda depois de aprovada.
3. `scripting.executeScript` com `func`/`files` vindos de uma mensagem; pior ainda com `world: "MAIN"`.
4. CSP do manifest alargado com um host externo em `script-src`.

**Injeção no DOM**
5. `innerHTML`, `outerHTML`, `insertAdjacentHTML`, `document.write` com qualquer dado não literal.
6. Framework: `dangerouslySetInnerHTML`, `v-html`, `bypassSecurityTrustHtml`, `:is` dinâmico.
7. `href`/`src` sem validar protocolo (`javascript:`, `data:`).

**Fronteiras de confiança** — é aqui que está o valor de um analista
8. `window.addEventListener("message")` sem verificar `event.origin` **e** `event.source`.
9. `chrome.runtime.onMessage` sem `sender.id === chrome.runtime.id`.
10. `onMessageExternal` + `externally_connectable` largo, ou verificação de origem por `includes()`/regex em vez de `new URL()`.
11. Dados lidos do DOM da página (`document.title`, meta tags, texto) tratados como confiáveis.

**Privilégio e dados**
12. Permissão pedida que excede a funcionalidade descrita — **ou que não é usada por código nenhum**.
13. `<all_urls>` / `*://*/*`, e `optional_host_permissions` largos com `permissions.request()` automático.
14. Que dados saem, para onde, sobre que protocolo, e se há identificador persistente do utilizador.
15. Segredos no bundle; `http://`; `sendBeacon`/`keepalive`.

**Sinais de escalar em vez de corrigir**: ativação temporizada ou condicional, strings base64/hex, domínios de fallback sem relação com a marca, verificações de segurança removidas entre versões, ofuscação.

---

## O método, em 6 passos

Decora a sequência. Se te perguntarem "como abordarias isto?" e responderes com um processo, ganhas mais do que com qualquer achado individual.

1. **Inventário** — manifest, versão, tamanho, dependências, autor, histórico.
2. **Privilégio** — o que esta extensão *pode* fazer, no pior caso, independentemente do que faz.
3. **Superfície** — que entradas não confiáveis chegam ao código privilegiado.
4. **Fluxo** — da fonte não confiável até ao sink perigoso; e dos dados do utilizador até à rede.
5. **Dinâmica** — correr numa VM, observar tráfego, confirmar hipóteses.
6. **Risco e escrita** — severidade, impacto, recomendação. Uma conclusão acionável.

---

## Modelo de relatório de achado

Usa este formato nas respostas escritas. Consistência de formato lê-se como experiência.

```
[ALTA] Execução de código remoto via template de configuração
Ficheiro:      sw.js:19, popup.js:13
Pré-condições: nenhuma — funcionalidade normal da extensão
Descrição:     A resposta de /config é guardada em texto no storage e
               posteriormente convertida em função executável com
               new Function(), no contexto privilegiado do popup.
Impacto:       Execução arbitrária com acesso a todas as APIs chrome.*,
               controlada pelo servidor, sem submeter nova versão à store.
               Agravado por A3 (HTTP em claro), que estende o controlo a
               qualquer atacante na rede.
Recomendação:  Remover new Function(). Configuração deve ser dados
               estruturados validados contra um schema, nunca código.
```

Três coisas que distinguem um bom relatório: **pré-condições** (o atacante precisa de quê?), **impacto concreto** (não "pode ser perigoso"), e **ligação a outros achados** (as cadeias é que importam).

---

## Os três erros que te custam a entrevista

1. **Listar achados sem os ligar.** Doze achados soltos < três cadeias de ataque explicadas.
2. **Propor sanitização quando a resposta é remover privilégio.** "Sanitizar o input do `postMessage`" é pior resposta que "porque é que isto aceita mensagens de páginas web?".
3. **Dizer "não é explorável" com confiança.** Diz antes: "não encontrei caminho de exploração em X minutos; as pré-condições seriam A e B". Calibração conta mais que certeza.
