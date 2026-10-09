// Versao corrigida - sem innerHTML, sem eval, sem segredos no codigo

function criarItem(nota) {
  const li = document.createElement("li");
  const titulo = document.createElement("b");
  titulo.textContent = nota.titulo;          // texto, nunca HTML
  const corpo = document.createElement("p");
  corpo.textContent = nota.texto;
  li.append(titulo, corpo);
  return li;
}

function render(notas) {
  const lista = document.getElementById("lista");
  lista.replaceChildren(...notas.map(criarItem));
}

async function tabActiva() {
  const [tab] = await chrome.tabs.query({ active: true, currentWindow: true });
  return tab;
}

async function carregar() {
  const tab = await tabActiva();
  document.getElementById("titulo").textContent = tab?.title ?? "";

  const { notas = [], prefs = {} } = await chrome.storage.local.get(["notas", "prefs"]);
  aplicarPrefs(prefs);   // dados, nao codigo
  render(notas);
}

// preferencias como estrutura de dados validada, nunca como string executavel
function aplicarPrefs(prefs) {
  const temas = { claro: "#ffffff", escuro: "#1e1e1e" };
  document.body.style.background = temas[prefs.tema] ?? temas.claro;
}

async function guardarNota() {
  const texto = document.getElementById("nota").value.slice(0, 5000);
  const tab = await tabActiva();
  const { notas = [] } = await chrome.storage.local.get("notas");
  notas.push({ titulo: tab?.title ?? "", texto, url: tab?.url ?? "" });
  await chrome.storage.local.set({ notas });
  render(notas);
  // sincronizacao remota: so com consentimento explicito, sobre HTTPS,
  // e com o token obtido via chrome.identity - nunca hardcoded no bundle.
}

document.addEventListener("DOMContentLoaded", () => {
  document.getElementById("guardar").addEventListener("click", guardarNota);
  carregar();
});
