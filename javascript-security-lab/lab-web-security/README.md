# Lab de preparação — Front-End Engineer (Web Security)

Material de treino para a vaga de análise de extensões web. Tudo aqui é **benigno**: as extensões do lab contêm padrões de código inseguros (do tipo que encontrarias num code review), não malware. O objetivo é treinares o raciocínio de revisor, não produzir amostras.

```
lab-web-security/
├── extensao-vulneravel/    # extensão MV3 com ~12 falhas plantadas
├── extensao-corrigida/     # a mesma extensão, revista
├── labs-frameworks/        # pares vulnerável/corrigido em React e Vue
└── ferramentas/
    ├── triagem.js          # scanner estático simples (node triagem.js ../extensao-vulneravel)
    └── checklist-code-review.md
```

---

## 1. O que a vaga realmente pede

Lê a descrição com atenção: **não é front-end**. O dia-a-dia é:

| Atividade | O que treinar |
|---|---|
| Review de extensões web | Modelo de segurança do Chrome/MV3, permissões, isolamento |
| Code review | Ler JS de outros (muitas vezes minificado/transpilado) e encontrar padrões perigosos |
| Análise estática | grep/semgrep/AST, mapear sinks e fluxo de dados |
| Análise dinâmica | DevTools, proxy HTTP, observar tráfego e comportamento em runtime |
| Avaliação de risco | Traduzir um achado em severidade e impacto, por escrito |

A formação inicial de ~2 meses existe precisamente porque não esperam um analista de malware formado. **O que esperam de ti é JavaScript profundo + método analítico + capacidade de escrever conclusões.** É aí que deves apostar.

---

## 2. Plano de 7 dias

**Dia 1 — Modelo de extensões**
Lê a documentação do Chrome sobre Manifest V3. Percebe e sabe explicar:
- contextos isolados: service worker vs content script vs página do popup vs página web
- "isolated world" — porque é que o content script vê o DOM mas não as variáveis JS da página
- porque é que MV3 proíbe código remoto e o que isso muda para quem escreve malware
- `permissions` vs `host_permissions` vs `optional_permissions`, e `activeTab`
- canais de mensagem: `runtime.sendMessage`, `onMessageExternal`, `window.postMessage`

**Dia 2 — Carregar e partir a extensão do lab**
`chrome://extensions` → modo de programador → "Carregar sem compactação" → `extensao-vulneravel/`.
Abre o popup, abre o service worker no DevTools, vê a consola. Faz o exercício da secção 4 **antes** de leres as soluções.

**Dia 3 — Ferramentas**
```bash
node ferramentas/triagem.js extensao-vulneravel      # o scanner do lab
npx semgrep --config p/javascript extensao-vulneravel
npx eslint --no-eslintrc --plugin security ...       # eslint-plugin-security, eslint-plugin-no-unsanitized
npx retire --path extensao-vulneravel                # dependências vulneráveis
```
Compara o que cada um apanha e o que falha. Saber *porque* uma ferramenta gera falsos positivos é meia entrevista.

**Dia 4 — Ler código que não foi escrito para ser lido**
Pega numa extensão real da Chrome Web Store (uma popular e open-source, ex.: uBlock Origin). Descarrega o `.crx`, muda a extensão para `.zip`, descompacta, e lê. Treina com bundles minificados: `prettier --write` para reformatar, procura source maps (`//# sourceMappingURL`), experimenta o `webcrack` para desempacotar webpack. Escreve meia página a descrever o que a extensão faz e que dados toca.

**Dia 5 — Análise dinâmica**
Corre a extensão vulnerável com um proxy (mitmproxy ou Burp) a observar o tráfego. Vê o pedido para `lab-sync.example.com` a sair com o token e as notas. Usa o separador Network do service worker. Experimenta `chrome://extensions` → "Ver detalhes" → permissões concedidas.

**Dia 6 — Frameworks**
`labs-frameworks/`: encontra os problemas nos ficheiros `*-vulneravel` antes de abrires os `*-corrigido`. Os sinks de framework aparecem muito em review: `dangerouslySetInnerHTML`, `v-html`, `bypassSecurityTrustHtml`, `:is` dinâmico, `href` controlado por dados.

**Dia 7 — Escrever**
Pega em 3 achados do lab e escreve-os como um relatório: título, severidade (CVSS ou alta/média/baixa com justificação), pré-condições, impacto, evidência, recomendação. Guarda isto — se te pedirem um exercício em casa, é este o formato.

---

## 3. Perguntas prováveis e o ângulo da resposta

**"Como analisarias uma extensão que te chega para review?"**
Dá-lhes um processo, não uma lista de comandos: 1) inventário — manifest, permissões, tamanho, dependências, autor, histórico de versões; 2) mapa de superfície — que entradas não confiáveis existem (páginas web, mensagens externas, rede) e que privilégios a extensão tem; 3) estática — sinks e fluxo até eles; 4) dinâmica — correr e observar tráfego/comportamento; 5) diff com a versão anterior; 6) risco e escrita.

**"O que torna uma permissão perigosa?"**
Não é a permissão isolada, é a combinação: `<all_urls>` + `cookies` + acesso à rede = roubo de sessão de qualquer site. `scripting` + mensagem não validada = qualquer página executa código privilegiado.

**"Porque é que o MV3 é mais seguro que o MV2?"**
Sem código remoto (o que mata o padrão "extensão limpa na review, payload entregue depois"), service workers efémeros em vez de páginas persistentes, `declarativeNetRequest` em vez de `webRequest` bloqueante, CSP mais estrito. Menciona também que não resolve tudo: a lógica maliciosa pode estar no bundle desde o início, ativada por condição.

**"Como é que uma página web pode atacar uma extensão?"**
`window.postMessage` para um content script que não valida `origin`/`source`; `onMessageExternal` com `externally_connectable` demasiado aberto; DOM envenenado que a extensão lê e reinjeta com `innerHTML`; `web_accessible_resources` para fazer fingerprint da extensão e depois entregar exploit direcionado.

**"Diferença entre XSS no DOM e XSS refletido?"**
Clássica, mas nesta função querem a variante no DOM: fonte (`location.hash`, `postMessage`, `document.title`) → sink (`innerHTML`, `eval`). Numa extensão, XSS no contexto do popup ou do service worker é escalada de privilégio, não apenas roubo de cookies.

**"Encontraste uma extensão suspeita. E agora?"**
Preserva a amostra e o hash, não a corres na máquina pessoal (VM isolada, snapshot), documenta indicadores (domínios, endpoints, strings), avalia se está a ser distribuída ativamente, escala segundo o processo — e não tentas "arranjar" a extensão. Mostrar contenção e cadeia de custódia vale mais do que mostrar bravura técnica.

**"Como te manténs a par?"**
Boletins do Chrome Security, blogs de análise de extensões, OWASP Top 10, relatórios de campanhas de extensões comprometidas (a compra de extensões populares por terceiros é um vetor recorrente).

**Pergunta que deves fazer tu:** como é o pipeline — as extensões chegam por fila de triagem, por deteção automática, por report? Que percentagem do trabalho é automação vs review manual? Isto mostra que percebeste que é um trabalho de escala.

---

## 4. Exercício: encontra as falhas em `extensao-vulneravel/`

Antes de correres o scanner, lê os 4 ficheiros e faz a tua lista. Depois compara.

<details>
<summary><b>Soluções (não espreites já)</b></summary>

**manifest.json**
1. `host_permissions: ["<all_urls>"]` — acesso a todos os sites sem justificação.
2. `permissions` excessivas: `cookies`, `webRequest`, `tabs` quando `activeTab` chegaria.
3. `externally_connectable: ["*://*/*"]` — qualquer site do mundo pode enviar comandos ao background.
4. `web_accessible_resources: ["*"]` para `<all_urls>` — expõe todos os ficheiros e permite fingerprint da extensão.
5. `content_scripts` com `matches: <all_urls>`, `all_frames: true`, `run_at: document_start` — superfície máxima.

**popup.js**
6. Token de API hardcoded no bundle (qualquer utilizador o extrai) e impresso na consola.
7. `innerHTML +=` com `n.titulo`/`n.texto` vindos de páginas visitadas → XSS no contexto privilegiado do popup.
8. `document.getElementById("titulo").innerHTML = tab.title` — o título da página é controlado pelo atacante.
9. `eval(data.prefs)` — preferências tratadas como código; quem escrever no storage executa código.
10. `fetch` sobre `http://` com o token no header → credencial em claro.
11. `onclick` inline no HTML — bloqueado pelo CSP do MV3; sinal de código escrito sem perceber o modelo.

**content.js**
12. `window.addEventListener("message")` sem validar `event.origin` nem `event.source`, e reencaminha a ação para o background → qualquer página conduz a extensão.
13. `insertAdjacentHTML` com HTML vindo de mensagem → injeção no DOM.
14. Captura de toda a seleção de texto do utilizador em cada `mouseup`, em todos os sites, enviada para fora — recolha desproporcionada.

**background.js**
15. `onMessageExternal` que lê cookies de qualquer domínio a pedido de qualquer site → roubo de sessão.
16. `chrome.tabs.create({ url: req.url })` sem validar protocolo → `javascript:`/`data:`.
17. `onMessage` sem verificar `sender.id`.
18. `new Function("return " + req.codigo)()` passado a `executeScript` → execução de código arbitrário em qualquer separador.
19. Telemetria para endpoint em claro com todo o payload do utilizador.

Agora abre `extensao-corrigida/` e vê cada correção. Repara que várias não são "sanitizar melhor" — são **remover a funcionalidade ou o privilégio**. Numa entrevista, propor reduzir permissões vale mais do que propor um sanitizador.

</details>

---

## 5. Como usar isto na conversa

Se te perguntarem o que fizeste para te preparares, tens resposta concreta: montaste uma extensão MV3 com falhas plantadas, escreveste um scanner de triagem, correste semgrep por cima e comparaste os resultados, e documentaste os achados em formato de relatório. Leva o repositório no GitHub. Para um perfil que vem de desenvolvimento e está a entrar em segurança aplicacional, isto é o sinal mais forte que podes dar.

> Aviso: a `extensao-vulneravel` é para carregares localmente num perfil de Chrome separado. Não a publiques nem a instales num perfil com sessões reais.
