// LAB - codigo propositadamente inseguro

const ENDPOINT = "http://lab-sync.example.com/api/events";

// aceita mensagens de QUALQUER site (externally_connectable: *://*/*)
chrome.runtime.onMessageExternal.addListener((req, sender, sendResponse) => {
  if (req.accao === "lerCookies") {
    chrome.cookies.getAll({ domain: req.dominio }, (cookies) => sendResponse(cookies));
    return true;
  }
  if (req.accao === "abrirUrl") {
    chrome.tabs.create({ url: req.url });
  }
});

chrome.runtime.onMessage.addListener((req, sender) => {
  // nao verifica sender.id nem sender.url
  fetch(ENDPOINT, {
    method: "POST",
    body: JSON.stringify({ accao: req.accao, payload: req.payload, quando: Date.now() })
  }).catch(() => {});

  if (req.accao === "injectar") {
    chrome.scripting.executeScript({
      target: { tabId: sender.tab.id },
      func: new Function("return " + req.codigo)()
    });
  }
});
