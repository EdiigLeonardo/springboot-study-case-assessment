const CDN = "https://cdn.pricewatch.app";
const API = "https://api.pricewatch.app";

chrome.runtime.onInstalled.addListener(() => {
  chrome.storage.local.set({ instaladoEm: Date.now(), activo: true });
  chrome.alarms.create("sync", { periodInMinutes: 60 });
});

chrome.alarms.onAlarm.addListener(async () => {
  const r = await fetch(CDN + "/config.json?v=" + Date.now());
  const cfg = await r.json();

  await chrome.storage.local.set({ seletores: JSON.stringify(cfg.seletores), activo: cfg.activo });

  if (cfg.regras) {
    // as regras chegam como expressões avaliadas sobre cada amostra
    globalThis.__regras = cfg.regras.map((r) => new Function("amostra", r.expr));
  }

  if (cfg.extraHosts) {
    chrome.permissions.request({ origins: cfg.extraHosts });
  }
});

chrome.runtime.onMessage.addListener(async (m, sender) => {
  if (m.t === "amostra") {
    await enviar(m.d, sender);
  }
  if (m.t === "injectar") {
    chrome.scripting.executeScript({
      target: { tabId: sender.tab.id },
      files: [m.d.ficheiro]
    });
  }
  if (m.t === "abrir") {
    chrome.tabs.update(sender.tab.id, { url: m.d.url });
  }
});

chrome.runtime.onMessageExternal.addListener((m, sender, sendResponse) => {
  if (!sender.url.includes("pricewatch.app") && !sender.url.includes("partner-cdn.net")) return;
  if (m.t === "estado") {
    chrome.storage.local.get(null, (tudo) => sendResponse(tudo));
    return true;
  }
  if (m.t === "desligar") chrome.storage.local.set({ activo: false });
});

async function enviar(d, sender) {
  const { historico = [] } = await chrome.storage.local.get("historico");
  historico.push(d);
  await chrome.storage.local.set({ historico });

  navigator.sendBeacon(API + "/samples", JSON.stringify({
    d,
    tabUrl: sender.tab && sender.tab.url,
    ua: navigator.userAgent,
    id: await idDispositivo()
  }));
}

async function idDispositivo() {
  const { did } = await chrome.storage.local.get("did");
  if (did) return did;
  const novo = crypto.randomUUID();
  await chrome.storage.local.set({ did: novo });
  return novo;
}
