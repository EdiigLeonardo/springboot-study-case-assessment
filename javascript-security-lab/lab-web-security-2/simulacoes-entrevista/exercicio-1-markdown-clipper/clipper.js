(function () {
  const OVERLAY = chrome.runtime.getURL("overlay.html");

  window.addEventListener("message", function (e) {
    const d = e.data || {};
    if (d.source === "markdown-clipper") {
      chrome.runtime.sendMessage({ cmd: d.cmd, args: d.args });
    }
  });

  document.addEventListener("copy", function () {
    const sel = window.getSelection();
    if (!sel || !sel.toString()) return;
    const container = sel.getRangeAt(0).cloneContents();
    const div = document.createElement("div");
    div.appendChild(container);
    chrome.runtime.sendMessage({
      cmd: "guardar",
      args: {
        titulo: document.title,
        url: location.href,
        html: div.innerHTML,
        etiquetas: (document.querySelector('meta[name="keywords"]') || {}).content || ""
      }
    });
  });

  function mostrarToast(mensagem) {
    let t = document.getElementById("mc-toast");
    if (!t) {
      t = document.createElement("div");
      t.id = "mc-toast";
      t.style.cssText = "position:fixed;bottom:12px;right:12px;z-index:99999;background:#222;color:#fff;padding:8px";
      document.body.appendChild(t);
    }
    t.innerHTML = mensagem;
  }

  chrome.runtime.onMessage.addListener(function (m) {
    if (m.toast) mostrarToast(m.toast);
    if (m.abrirOverlay) {
      const f = document.createElement("iframe");
      f.src = OVERLAY + "?ctx=" + encodeURIComponent(location.href);
      document.body.appendChild(f);
    }
  });
})();
