chrome.runtime.onMessage.addListener((m) => {
  if (m.t !== "render") return;
  const caixa = document.createElement("div");
  caixa.className = "ts-sugestao";
  caixa.innerHTML = m.html;
  document.body.appendChild(caixa);
});

const meta = document.querySelector('meta[name="ts-hint"]');
if (meta) {
  chrome.runtime.sendMessage({ t: "sugestao", html: meta.content });
}
