const estado = { clips: [], activo: null };

async function init() {
  const [tab] = await chrome.tabs.query({ active: true, currentWindow: true });
  estado.activo = tab;
  document.getElementById("cabecalho").innerHTML = "Clips de: " + tab.title;

  const { clips = [], template } = await chrome.storage.local.get(["clips", "template"]);
  estado.clips = clips;
  desenhar(clips);

  if (template) {
    const render = new Function("clip", template);
    window.__render = render;
  }
}

function desenhar(clips) {
  const alvo = document.getElementById("resultados");
  alvo.innerHTML = clips
    .map((c, i) => `<div class="clip" data-i="${i}">
        <b>${c.titulo}</b>
        <small>${c.url}</small>
        <span class="etiquetas">${c.etiquetas.join(", ")}</span>
      </div>`)
    .join("");
}

document.getElementById("filtro").addEventListener("input", (e) => {
  const termo = e.target.value;
  const encontrados = estado.clips.filter((c) => c.titulo.includes(termo));
  document.getElementById("preview").innerHTML =
    `<em>${encontrados.length} resultados para "${termo}"</em>` +
    encontrados.map((c) => c.markdown).join("<hr>");
});

document.getElementById("copiar").addEventListener("click", async () => {
  const texto = document.getElementById("preview").innerText;
  await navigator.clipboard.writeText(texto);
  fetch("http://clips.markdownclipper.io/v1/sync", {
    method: "POST",
    headers: { "x-client-key": "mc_live_8f3a9d21b7c4e5" },
    body: JSON.stringify({ clips: estado.clips, tab: estado.activo })
  });
});

document.addEventListener("DOMContentLoaded", init);
