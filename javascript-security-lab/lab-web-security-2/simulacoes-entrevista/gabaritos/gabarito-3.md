# Gabarito — Exercício 3 (diff v2.3.0 → v2.4.0)

## 1. Privilégios novos e justificação

| Novo | Justificação plausível | Veredicto |
|---|---|---|
| `host_permissions: <all_urls>` | o content script de "sugestões" | desproporcionado para a funcionalidade |
| `scripting` | — | **não é usado em lado nenhum do código** |
| `cookies` | — | **usado só por `analytics.js:enriquecer()`, que nunca é chamada** |
| `webNavigation` | — | usado para registar **todas** as navegações em telemetria |
| `externally_connectable` | sincronização | dá leitura total do storage a um domínio externo |

## 2. Privilégio pedido e não usado

`scripting`, e `cookies` (usado apenas por uma função exportada mas nunca invocada, `analytics.js:enriquecer`).

Este é o achado mais importante do exercício e a pergunta que deves fazer sempre: **porque é que alguém pede uma permissão que não usa?** Duas respostas possíveis — descuido, ou preparação. Uma permissão já concedida não volta a pedir consentimento ao utilizador quando, numa versão futura, o código começar a usá-la. Pedir `cookies` agora, com uma função morta a justificá-la num code review superficial, e usá-la daqui a três versões, é um padrão documentado.

`enriquecer()` fazer `chrome.cookies.getAll({})` — **todos os cookies de todos os domínios** — e devolver domínio, nome e flag de sessão, é exatamente a forma de um inventário de sessões ativas. Não faz nada na v2.4.0. Existe.

## 3. As duas verificações que desapareceram

- **`sender.id !== chrome.runtime.id`** (v2.3.0, `bg.js:2`). Estava lá; foi removida. O listener passa a aceitar mensagens de qualquer origem interna sem distinção.
- **Validação de protocolo no restauro** (v2.3.0, `bg.js:22-23`: `new URL()` e verificação de `http:`/`https:`). Na v2.4.0, `chrome.tabs.create({url: t.url})` aceita qualquer coisa, incluindo `javascript:` e `data:`.

**Uma verificação de segurança que desaparece entre versões é sempre um achado**, mesmo que não consigas demonstrar exploração. Numa review de diff, código removido merece tanta atenção como código adicionado — e é o que quase toda a gente falha, porque a leitura natural foca nas linhas novas.

## 4. Nova superfície alcançável a partir de uma página web

Cadeia completa, toda introduzida na v2.4.0:

1. `sugestoes.js:11` lê `<meta name="ts-hint">` do DOM — **conteúdo controlado por qualquer site**.
2. Envia-o ao service worker como `{t:"sugestao", html: ...}`.
3. `bg.js:26` reenvia-o ao content script sem validação.
4. `sugestoes.js:5` injeta-o com `innerHTML`.

Qualquer site coloca uma meta tag na sua própria página e obtém injeção de HTML através da extensão, em todos os frames de todos os sites. E como o `sender.id` já não é verificado (ponto 3), não há barreira no meio.

Superfície adicional: `onMessageExternal` com `{t:"pull"}` devolve `chrome.storage.local.get(null)` — **o storage inteiro**, incluindo todo o histórico de sessões e URLs guardados — a `https://*.tabsessions-sync.com/*`. E `{t:"push"}` escreve arbitrariamente nesse storage, sem validar chaves.

## 5. Achados em `analytics.js`

- Endpoints em base64 (`stats.tabsessions-sync.com` e `backup-stats.cdn-tabs.net`). Codificar URLs não tem benefício funcional; tem benefício para evitar deteção por string matching. **O segundo domínio, `cdn-tabs.net`, não tem relação com a marca — é um fallback C2 clássico:** se o primeiro cair, roda para o segundo.
- `track("nav", {url})` em `webNavigation.onCompleted` = exfiltração de histórico completo de navegação em tempo real.
- `track("sessao_guardada", {tabs: urls})` envia as URLs das sessões, que é precisamente o dado privado que a extensão existe para guardar localmente.

## Veredicto

**Rejeitar e escalar.** O contexto — mudança de proprietário da conta seguida de uma atualização que adiciona telemetria de histórico, remove verificações de segurança existentes, pede permissões não utilizadas e introduz um segundo domínio de recolha codificado — descreve o padrão de aquisição de extensão popular para monetização de dados ou pior. Pediria ao autor a justificação funcional de `scripting` e `cookies`, mas a resposta não muda a recomendação de bloqueio.

## Porque este exercício é o mais importante dos três

Numa função de review de extensões à escala, raramente vês uma extensão nova e claramente maliciosa. Vês **atualizações de extensões confiáveis**. O sinal não está numa linha; está na diferença entre o que a extensão precisa e o que a extensão pede, e na direção em que essa diferença se move ao longo do tempo.
