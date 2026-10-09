// Versao corrigida

const ORIGEM_CONFIAVEL = "https://developer.mozilla.org";

window.addEventListener("message", (event) => {
  if (event.origin !== ORIGEM_CONFIAVEL) return;      // valida a origem
  if (event.source !== window) return;                // ignora iframes
  const msg = event.data;
  if (typeof msg !== "object" || msg === null) return;
  if (msg.tipo !== "notas:comando") return;

  const ACCOES = new Set(["guardar", "listar"]);       // allow-list
  if (!ACCOES.has(msg.accao)) return;

  chrome.runtime.sendMessage({ accao: msg.accao });    // sem payload arbitrario
});

function mostrarBanner(texto) {
  const banner = document.createElement("div");
  banner.textContent = String(texto).slice(0, 200);    // texto, nunca HTML
  banner.style.cssText = "position:fixed;top:0;right:0;z-index:2147483647;background:#eee;padding:4px";
  document.body.appendChild(banner);
  setTimeout(() => banner.remove(), 4000);
}

chrome.runtime.onMessage.addListener((req) => {
  if (req?.tipo === "banner") mostrarBanner(req.texto);
});

// a seleccao do utilizador so e lida quando ele pede, nao em cada mouseup
