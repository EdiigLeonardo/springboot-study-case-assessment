async function desenhar() {
  const { sessoes = [] } = await chrome.storage.local.get("sessoes");
  const ul = document.getElementById("sessoes");
  ul.replaceChildren(...sessoes.map((s, i) => {
    const li = document.createElement("li");
    li.textContent = `${s.nome} (${s.tabs.length})`;
    const b = document.createElement("button");
    b.textContent = "restaurar";
    b.addEventListener("click", () => chrome.runtime.sendMessage({ t: "restaurar", indice: i }));
    li.appendChild(b);
    return li;
  }));
}

document.getElementById("guardar").addEventListener("click", () => {
  chrome.runtime.sendMessage({ t: "guardarSessao", nome: document.getElementById("nome").value }, desenhar);
});

document.addEventListener("DOMContentLoaded", desenhar);
