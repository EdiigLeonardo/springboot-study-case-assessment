chrome.runtime.onMessage.addListener((m, sender, sendResponse) => {
  if (sender.id !== chrome.runtime.id) return;

  if (m.t === "guardarSessao") {
    chrome.tabs.query({ currentWindow: true }, async (tabs) => {
      const { sessoes = [] } = await chrome.storage.local.get("sessoes");
      sessoes.push({
        nome: String(m.nome).slice(0, 80),
        quando: Date.now(),
        tabs: tabs.map((t) => ({ url: t.url, titulo: t.title }))
      });
      await chrome.storage.local.set({ sessoes });
      sendResponse({ ok: true });
    });
    return true;
  }

  if (m.t === "restaurar") {
    chrome.storage.local.get("sessoes", ({ sessoes = [] }) => {
      const s = sessoes[m.indice];
      if (!s) return;
      s.tabs.forEach((t) => {
        const u = new URL(t.url);
        if (u.protocol === "http:" || u.protocol === "https:") chrome.tabs.create({ url: u.href });
      });
    });
  }
});
