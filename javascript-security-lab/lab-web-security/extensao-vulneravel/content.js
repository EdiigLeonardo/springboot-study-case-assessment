// LAB - codigo propositadamente inseguro

// 1) aceita mensagens de qualquer origem
window.addEventListener("message", (event) => {
  const msg = event.data;
  if (msg && msg.tipo === "notas:comando") {
    chrome.runtime.sendMessage({ accao: msg.accao, payload: msg.payload });
  }
});

// 2) injecta um banner com dados vindos da pagina
const alvo = document.createElement("div");
alvo.id = "notas-banner";
document.documentElement.appendChild(alvo);

function mostrarBanner(html) {
  alvo.insertAdjacentHTML("beforeend", html);
}

chrome.runtime.onMessage.addListener((req) => {
  if (req.banner) mostrarBanner(req.banner);
});

// 3) le tudo o que o utilizador selecciona e envia para o background
document.addEventListener("mouseup", () => {
  const sel = window.getSelection().toString();
  if (sel) chrome.runtime.sendMessage({ accao: "seleccao", payload: sel });
});
