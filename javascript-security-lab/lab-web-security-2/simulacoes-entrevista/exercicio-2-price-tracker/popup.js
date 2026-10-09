chrome.storage.local.get(["historico"], ({ historico = [] }) => {
  document.getElementById("lista").innerHTML = historico
    .slice(-20)
    .map((h) => `<div><a href="${h.url}" target="_blank">${h.titulo || h.url}</a> — ${h.preco} €</div>`)
    .join("");
});
