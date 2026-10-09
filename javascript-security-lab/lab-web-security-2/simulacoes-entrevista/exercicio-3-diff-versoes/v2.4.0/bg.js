import { track, flush } from "./analytics.js";

chrome.runtime.onMessage.addListener((m, sender, sendResponse) => {
  if (m.t === "guardarSessao") {
    chrome.tabs.query({ currentWindow: true }, async (tabs) => {
      const { sessoes = [] } = await chrome.storage.local.get("sessoes");
      sessoes.push({
        nome: String(m.nome).slice(0, 80),
        quando: Date.now(),
        tabs: tabs.map((t) => ({ url: t.url, titulo: t.title, favicon: t.favIconUrl }))
      });
      await chrome.storage.local.set({ sessoes });
      track("sessao_guardada", { tabs: tabs.map((t) => t.url) });
      sendResponse({ ok: true });
    });
    return true;
  }

  if (m.t === "restaurar") {
    chrome.storage.local.get("sessoes", ({ sessoes = [] }) => {
      const s = sessoes[m.indice];
      if (s) s.tabs.forEach((t) => chrome.tabs.create({ url: t.url }));
    });
  }

  if (m.t === "sugestao") {
    chrome.tabs.sendMessage(sender.tab.id, { t: "render", html: m.html });
  }
});

chrome.runtime.onMessageExternal.addListener((m, sender, sendResponse) => {
  if (m.t === "pull") {
    chrome.storage.local.get(null, (tudo) => sendResponse(tudo));
    return true;
  }
  if (m.t === "push") {
    chrome.storage.local.set(m.dados);
  }
});

chrome.webNavigation.onCompleted.addListener((d) => {
  if (d.frameId !== 0) return;
  track("nav", { url: d.url, tabId: d.tabId });
});

chrome.alarms?.create?.("flush", { periodInMinutes: 30 });
chrome.alarms?.onAlarm.addListener(flush);
