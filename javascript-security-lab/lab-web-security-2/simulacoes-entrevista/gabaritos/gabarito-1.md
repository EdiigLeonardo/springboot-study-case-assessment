# Gabarito — Exercício 1 (Markdown Clipper)

Compara com a tua lista antes de leres a justificação de cada ponto. **Se apanhaste 8 dos 12, estás pronto para o exercício 2.**

## Achados

### A1 — `host_permissions: ["*://*/*"]` + content script em todos os sites — ALTA
A funcionalidade descrita (copiar seleção) não precisa de correr em todos os sites em permanência. `activeTab` + `chrome.action.onClicked` dava exatamente a mesma funcionalidade com uma fração do privilégio. **Regra: quando o privilégio pedido excede a funcionalidade descrita, é achado por si só — mesmo que não encontres nenhum abuso no código.**

### A2 — `sw.js:41` — telemetria de todo o histórico de navegação — ALTA
`chrome.tabs.onUpdated` envia URL e título de **todos** os separadores, em todos os sites, para um endpoint. Isto não é "copiar seleções": é recolha de histórico de navegação. Não declarado na descrição. Em contexto de store, é motivo de rejeição por si só.

### A3 — `sw.js:2` e `popup.js:34` — tráfego em HTTP — ALTA
`http://clips.markdownclipper.io`. Os dados de navegação e a chave de cliente vão em claro. Qualquer pessoa na mesma rede lê e altera. E como o `config` (A4) vem deste endpoint, quem fizer MITM controla a extensão.

### A4 — `sw.js:19` + `popup.js:13` — código remoto via template — ALTA, a mais grave
Cadeia completa:
1. `case "config"` faz fetch de `/config` e guarda a resposta **em texto** em `storage.template`.
2. `popup.js` faz `new Function("clip", template)` sobre esse texto.
Resultado: o servidor (ou quem fizer MITM sobre HTTP, ver A3) injeta código que corre no contexto privilegiado do popup, com acesso a todas as APIs `chrome.*`. Isto é exatamente o que o MV3 proíbe ao banir código remoto — e é o padrão nº1 de extensão que passa na review e só depois se torna maliciosa. **Se só apanhares um achado, que seja este.**

### A5 — `sw.js:14` — `executeScript` com `world: "MAIN"` e `window.eval(src)` — ALTA
Qualquer mensagem `{cmd:"executar"}` executa código arbitrário no contexto da página, em todos os frames. Combinado com A6 (qualquer página pode enviar essa mensagem), fecha-se a cadeia: site malicioso → content script → service worker → execução de código em qualquer separador.

### A6 — `clipper.js:5` — `postMessage` sem validar origem — ALTA
Não há verificação de `e.origin` nem de `e.source`, e reencaminha `cmd`/`args` diretamente para o service worker. Qualquer página em que a extensão corra (ou seja, todas, ver A1) controla o `switch` do `sw.js`. É a ponte entre a web não confiável e o privilégio da extensão.

### A7 — `sw.js:11` — `chrome.tabs.create({url: msg.args.url})` sem validar protocolo — MÉDIA
Permite `javascript:`, `data:`, `chrome-extension://...`. Redirect controlado por atacante.

### A8 — `popup.js:8, 22, 30` — XSS no DOM no contexto do popup — ALTA
`innerHTML` com `tab.title` (controlado por qualquer página), com `c.titulo`/`c.url`/`c.etiquetas` (vindos do DOM das páginas visitadas) e com `c.markdown` (que é HTML bruto guardado em `sw.js:24`). XSS aqui não é roubo de cookie: é execução no contexto que tem acesso às APIs `chrome.*`. Escalada de privilégio.

### A9 — `clipper.js:37` — `mostrarToast` com `innerHTML` a partir de mensagem — MÉDIA
Injeção no DOM da página. Menos grave que A8 porque o content script está no isolated world, mas ainda permite defacement e phishing na página da vítima.

### A10 — `overlay.html` + `web_accessible_resources` — MÉDIA
`overlay.html` é acessível a qualquer site e escreve `location.search` com `innerHTML`. Qualquer página abre `chrome-extension://<id>/overlay.html?ctx=<payload>` e obtém XSS numa página **da extensão**. Além disso, ter recursos acessíveis permite a qualquer site detetar que tens a extensão instalada (fingerprint).

### A11 — `popup.js:36` — chave de API no bundle — MÉDIA
`mc_live_8f3a9d21b7c4e5` está em texto no código distribuído a todos os utilizadores. Qualquer um a extrai e usa contra a API.

### A12 — `clipper.js:13` — recolha excessiva no evento `copy` — MÉDIA
Captura o HTML de tudo o que o utilizador copia, em qualquer site — incluindo webmail, bancos, ferramentas internas. Mesmo com intenção legítima, é desproporcionado e é uma questão de RGPD, não só técnica.

## Veredicto esperado

**Rejeitar.** Não é "aprovar com alterações": há três padrões (A2, A4, A5) que, juntos, constituem uma arquitetura de controlo remoto sobre todos os sites do utilizador. Numa fila de triagem real, esta extensão sairia da revisão normal e iria para análise aprofundada.

## Como isto se apresenta na entrevista

Se listares os 12 achados por ordem aleatória, mostras que sabes ler. Se os apresentares **agrupados por cadeia de ataque** — "existe um caminho completo de página web até execução privilegiada: A6 → A5, alimentado por A1" — mostras que pensas como analista. É a diferença entre passar e impressionar.
