# Checklist de code review de extensões de browser

Percorre sempre pela mesma ordem: **privilégio → entrada → sink → destino**.

## 1. Manifest (privilégio)
- [ ] `manifest_version` é 3? (MV2 está a ser descontinuado)
- [ ] `permissions` são o mínimo necessário? `activeTab` em vez de `tabs`?
- [ ] `host_permissions` restritos, ou `<all_urls>` / `*://*/*`?
- [ ] APIs sensíveis: `cookies`, `webRequest`, `debugger`, `proxy`, `nativeMessaging`, `history`, `clipboardRead`, `downloads`
- [ ] `externally_connectable` — que sites podem falar com a extensão?
- [ ] `web_accessible_resources` — expõe ficheiros a qualquer página? (fingerprinting da extensão)
- [ ] `content_security_policy` alterado para permitir `unsafe-eval` / `unsafe-inline`?
- [ ] `content_scripts`: `matches` largos, `all_frames: true`, `run_at: document_start`?
- [ ] O que o manifest pede corresponde ao que a descrição na store promete?

## 2. Entradas não confiáveis
- [ ] `window.addEventListener("message", ...)` valida `event.origin` **e** `event.source`?
- [ ] `chrome.runtime.onMessage` valida `sender.id === chrome.runtime.id`?
- [ ] `onMessageExternal` valida `sender.url` contra uma allow-list?
- [ ] DOM da página (`document.title`, `innerText`, atributos, URL, `location.hash`) tratado como input hostil?
- [ ] Respostas de rede validadas antes de usadas?

## 3. Sinks perigosos
- [ ] `eval`, `new Function`, `setTimeout("string")`
- [ ] `innerHTML`, `outerHTML`, `insertAdjacentHTML`, `document.write`, `$(...).html()`
- [ ] `chrome.scripting.executeScript` com código/função vinda de mensagem
- [ ] `chrome.tabs.create/update` com URL não validada (`javascript:`, `data:`)
- [ ] Frameworks: `v-html` (Vue), `dangerouslySetInnerHTML` (React), `bypassSecurityTrust*` (Angular)

## 4. Dados e destino
- [ ] Que dados saem da máquina? Para que domínio? Sobre HTTPS?
- [ ] Recolha de histórico, URLs completos, conteúdo de formulários, seleção de texto, teclas?
- [ ] Segredos/tokens embebidos no bundle?
- [ ] Há telemetria não declarada na política de privacidade?

## 5. Sinais de código malicioso (para escalar, não para "arranjar")
- [ ] Código ofuscado, packers, strings base64/hex grandes, `String.fromCharCode` em cadeia
- [ ] Carregamento de código remoto (proibido em MV3): `import()` dinâmico para URL externo, injeção de `<script src>` externo
- [ ] Lógica condicionada (só ativa passados N dias, ou fora de ambientes de análise)
- [ ] Deteção de devtools / anti-debug
- [ ] Domínios C2 gerados dinamicamente (DGA), endpoints em pastebin/telegram/discord webhooks
- [ ] Mudança de comportamento entre versões (compara `v1.2` com `v1.3` — vetor clássico de compra de extensões)
