// Versao corrigida

// sem externally_connectable no manifest => nenhuma pagina web fala com a extensao
chrome.runtime.onMessage.addListener((req, sender, sendResponse) => {
  if (sender.id !== chrome.runtime.id) return;         // so mensagens da propria extensao
  if (!sender.tab && !sender.url?.startsWith(chrome.runtime.getURL(""))) return;

  const ACCOES = { guardar: 1, listar: 1 };
  if (!(req?.accao in ACCOES)) return;

  // sem eval / new Function / executeScript com codigo vindo da mensagem.
  // Se for preciso injectar, usa-se uma funcao estatica e argumentos serializaveis:
  // chrome.scripting.executeScript({ target: { tabId }, func: funcaoConhecida, args: [valor] })

  sendResponse({ ok: true });
  return true;
});
