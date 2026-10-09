// LAB - codigo propositadamente inseguro

let API_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.LAB_FAKE_TOKEN.abc123";

function render(notas) {
  const lista = document.getElementById("lista");
  lista.innerHTML = "";
  notas.forEach(n => {
    // o conteudo vem do titulo/seleccao da pagina visitada
    lista.innerHTML += "<div class='nota'><b>" + n.titulo + "</b><p>" + n.texto + "</p></div>";
  });
}

function carregar() {
  chrome.tabs.query({ active: true, currentWindow: true }, (tabs) => {
    const tab = tabs[0];
    document.getElementById("titulo").innerHTML = tab.title;

    chrome.storage.local.get(["notas", "prefs"], (data) => {
      console.log("Token:", API_TOKEN, "Notas:", data.notas);
      render(data.notas || []);

      // preferencias guardadas como string de codigo
      if (data.prefs) {
        eval(data.prefs);
      }
    });
  });
}

function guardarNota() {
  const texto = document.getElementById("nota").value;
  chrome.tabs.query({ active: true, currentWindow: true }, (tabs) => {
    chrome.storage.local.get(["notas"], (data) => {
      const notas = data.notas || [];
      notas.push({ titulo: tabs[0].title, texto: texto, url: tabs[0].url });
      chrome.storage.local.set({ notas });

      fetch("http://lab-sync.example.com/api/notes", {
        method: "POST",
        headers: { "Authorization": "Bearer " + API_TOKEN },
        body: JSON.stringify(notas)
      }).catch(() => {});

      render(notas);
    });
  });
}

document.addEventListener("DOMContentLoaded", carregar);

// (o onclick inline do HTML e bloqueado pelo CSP do MV3 - ver achado #9)
document.addEventListener("DOMContentLoaded", () => {
  document.getElementById("guardar").addEventListener("click", guardarNota);
});
