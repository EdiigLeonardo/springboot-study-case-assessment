# Gabarito — Exercício 2 (PriceWatch)

Este exercício é mais próximo do real: o código está *quase* correto. Muitos achados não são bugs — são decisões de arquitetura que criam capacidade de abuso. É esse o salto de "programador que sabe segurança" para "analista".

## 1. "Só lê o preço em três lojas" — falso

- `cs.js:24` — `dados.cookies = document.cookie` é enviado com cada amostra. Não tem nada a ver com preços.
- `cs.js:23` — `document.referrer` idem: revela o caminho de navegação do utilizador.
- `bg.js:52` — cada amostra vai com `sender.tab.url`, `navigator.userAgent` e um **identificador persistente de dispositivo** (`idDispositivo`, `crypto.randomUUID` guardado em storage). Isto transforma dados de preço em perfil rastreável de utilizador.
- `manifest.json` — `optional_host_permissions: ["*://*/*"]` mais `permissions.request()` automático (ver ponto 3) significa que o âmbito de três lojas é temporário por desenho.

## 2. O que sai e para onde

| Dado | Origem | Destino |
|---|---|---|
| Preço, título, stock | seletores da página | `api.pricewatch.app/samples` |
| `document.cookie` | página da loja | idem |
| `document.referrer`, URL do separador | navegador | idem |
| User agent + UUID persistente | dispositivo | idem |
| Todo o `storage.local` (histórico completo) | extensão | qualquer origem `*.pricewatch.app` **ou `*.partner-cdn.net`** via `onMessageExternal` |

O `sendBeacon` com `keepalive` é escolha deliberada: sobrevive ao fecho do separador e não aparece com destaque no separador Network se não estiveres à procura.

## 3. Mecanismo de alteração de comportamento pós-aprovação — SIM, três

Esta é a pergunta central. A cadeia:

1. **`bg.js:11-22` — configuração remota.** De hora a hora, busca `cdn.pricewatch.app/config.json` e escreve `seletores` e `activo` no storage.
2. **`bg.js:19` — `new Function(r.expr)`.** As "regras" chegam do servidor como **expressões em texto** e são convertidas em funções executáveis. Isto é código remoto, proibido em MV3, e contorna a review inteiramente: o que passa na store é um interpretador vazio.
3. **`bg.js:22` — `chrome.permissions.request({origins: cfg.extraHosts})`.** O servidor decide que hosts pedir. O `optional_host_permissions: ["*://*/*"]` no manifest existe precisamente para permitir que esse pedido seja `<all_urls>`. Um clique do utilizador numa caixa de diálogo e a extensão passa de 3 lojas para toda a web.
4. **`cs.js:22` — seletores remotos.** `PW.sel()` (`vendor.js`) aplica seletores CSS arbitrários e extrai `textContent || value` de qualquer elemento. Com seletores vindos do servidor, isto lê **qualquer campo de qualquer página** onde a extensão tenha permissão — incluindo campos de formulário. A extensão não precisa de código malicioso: o código é genérico e a maldade é configuração.

## 4. Sinais para escalar (em vez de pedir correções)

- **`cs.js:13-16` — ativação diferida.** A recolha só arranca 10 dias após a instalação. Não existe razão funcional para isto. Existe uma razão para um atacante: sobreviver à janela de análise, tanto automática como manual. **Este é o achado que, sozinho, justifica escalar.**
- **`cs.js:2` — configuração em base64 no código.** Inofensiva quando descodificada, mas a prática de codificar configuração serve para evitar deteção por assinatura textual.
- **`bg.js:35` — `partner-cdn.net`** aceite como origem externa. Domínio genérico, sem relação com a marca, com capacidade de ler todo o storage e desligar a extensão remotamente. É o padrão de terceiro que compra acesso a uma extensão.
- **`bg.js:37` — `sender.url.includes("pricewatch.app")`.** Verificação de origem por `includes` em vez de parsing de URL: `https://pricewatch.app.evil.com/` passa. Bypass trivial.
- `vendor.js` apresenta-se como biblioteca de helpers mas contém `d()` (descodificador base64), `post()` com `keepalive` e `sel()` (extrator genérico por seletores). É um kit de recolha disfarçado de utilitário.

## Achados técnicos adicionais

- `manifest.json` — CSP alargado para permitir `https://cdn.pricewatch.app` como fonte de scripts, e `popup.html` carrega `widgets.js` desse CDN: **código remoto no contexto privilegiado**, segundo caminho independente do ponto 3.
- `cs.js:38` — `PW.tpl(m.html, m.ctx)` injetado com `innerHTML`: XSS no DOM.
- `cs.js:32` — `window.addEventListener("message")` sem validar `origin`/`source`, reencaminha `t` e `d` arbitrários ao service worker; combinado com `bg.js:29` (`executeScript` com `files: [m.d.ficheiro]`) e `bg.js:32` (`tabs.update` com URL não validada), a página controla a extensão.
- `popup.js` — `innerHTML` com `h.url` e `h.titulo` vindos de páginas; `href` sem validação de protocolo.
- `bg.js:26` — `onMessage` sem verificar `sender.id`.

## Veredicto

**Escalar, não rejeitar simplesmente.** A combinação de ativação temporizada + configuração remota executável + escalada de permissões dirigida pelo servidor descreve uma extensão desenhada para mudar de comportamento depois de aprovada. A recomendação correta é: bloquear a atualização, preservar a amostra, comparar com as versões anteriores publicadas, e verificar que outras extensões partilham o `partner-cdn.net`.

## A frase que vale a entrevista

> "O código, tal como está, não faz nada claramente malicioso. O problema é que foi construído para poder passar a fazer, sem submeter nova versão. Estou a avaliar capacidade, não comportamento observado."
