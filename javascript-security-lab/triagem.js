#!/usr/bin/env node
/**
 * triagem.js - triagem estatica minima de uma extensao de browser.
 * Uso: node triagem.js ../extensao-vulneravel
 *
 * Nao substitui semgrep/eslint; serve para perceberes o raciocinio:
 * sinks perigosos -> origem dos dados -> privilegio que a extensao tem.
 */
const fs = require("fs");
const path = require("path");

const REGRAS = [
  { id: "EXEC-001", sev: "ALTA",  re: /\beval\s*\(/g,                         nota: "eval() executa codigo arbitrario" },
  { id: "EXEC-002", sev: "ALTA",  re: /new\s+Function\s*\(/g,                 nota: "new Function() equivale a eval" },
  { id: "EXEC-003", sev: "ALTA",  re: /setTimeout\s*\(\s*["'`]/g,             nota: "setTimeout com string = eval" },
  { id: "DOM-001",  sev: "ALTA",  re: /\.innerHTML\s*[+]?=/g,                 nota: "sink de XSS no DOM" },
  { id: "DOM-002",  sev: "ALTA",  re: /insertAdjacentHTML|outerHTML\s*=|document\.write/g, nota: "sink de XSS no DOM" },
  { id: "MSG-001",  sev: "ALTA",  re: /addEventListener\s*\(\s*["']message["']/g, nota: "verifica event.origin e event.source" },
  { id: "MSG-002",  sev: "ALTA",  re: /onMessageExternal/g,                   nota: "aceita mensagens de paginas web; verificar sender" },
  { id: "MSG-003",  sev: "MEDIA", re: /onMessage\.addListener/g,              nota: "verificar sender.id / sender.url" },
  { id: "NET-001",  sev: "MEDIA", re: /["'`]http:\/\//g,                      nota: "trafego em claro" },
  { id: "NET-002",  sev: "ALTA",  re: /fetch\s*\(|XMLHttpRequest|navigator\.sendBeacon/g, nota: "exfiltracao potencial: para onde vai e o que leva?" },
  { id: "API-001",  sev: "ALTA",  re: /chrome\.cookies|chrome\.debugger|chrome\.proxy|chrome\.webRequest/g, nota: "API sensivel" },
  { id: "API-002",  sev: "ALTA",  re: /executeScript/g,                       nota: "injeccao de codigo em paginas" },
  { id: "SEC-001",  sev: "ALTA",  re: /(api[_-]?key|token|secret|password)\s*[:=]\s*["'`][^"'`]{8,}/gi, nota: "segredo embebido no bundle" },
  { id: "OFU-001",  sev: "MEDIA", re: /atob\s*\(|fromCharCode|\\x[0-9a-f]{2}/gi, nota: "possivel ofuscacao / string codificada" },
];

const PERM_RISCO = {
  "<all_urls>": "ALTA", "tabs": "MEDIA", "cookies": "ALTA", "webRequest": "ALTA",
  "debugger": "ALTA", "proxy": "ALTA", "scripting": "MEDIA", "history": "ALTA",
  "clipboardRead": "ALTA", "downloads": "MEDIA", "nativeMessaging": "ALTA",
};

function ficheiros(dir) {
  return fs.readdirSync(dir, { withFileTypes: true }).flatMap((e) => {
    const p = path.join(dir, e.name);
    if (e.isDirectory()) return e.name === "node_modules" ? [] : ficheiros(p);
    return /\.(js|mjs|ts|html|json)$/.test(e.name) ? [p] : [];
  });
}

function linhaDe(txt, idx) { return txt.slice(0, idx).split("\n").length; }

function main() {
  const raiz = path.resolve(process.argv[2] || ".");
  const achados = [];

  const mPath = path.join(raiz, "manifest.json");
  if (fs.existsSync(mPath)) {
    const m = JSON.parse(fs.readFileSync(mPath, "utf8"));
    const perms = [...(m.permissions || []), ...(m.host_permissions || [])];
    for (const p of perms) {
      const sev = PERM_RISCO[p] || (p.includes("*") ? "ALTA" : null);
      if (sev) achados.push({ sev, id: "PERM", file: "manifest.json", line: 0, nota: `permissao ${p}` });
    }
    const ec = m.externally_connectable?.matches || [];
    if (ec.some((x) => x.includes("*://*/"))) {
      achados.push({ sev: "ALTA", id: "PERM", file: "manifest.json", line: 0, nota: "externally_connectable aberto a qualquer site" });
    }
    if (m.manifest_version === 2) {
      achados.push({ sev: "MEDIA", id: "PERM", file: "manifest.json", line: 0, nota: "Manifest V2 (obsoleto)" });
    }
  }

  for (const f of ficheiros(raiz)) {
    const txt = fs.readFileSync(f, "utf8");
    for (const r of REGRAS) {
      r.re.lastIndex = 0;
      let mt;
      while ((mt = r.re.exec(txt)) !== null) {
        achados.push({ sev: r.sev, id: r.id, file: path.relative(raiz, f), line: linhaDe(txt, mt.index), nota: r.nota });
      }
    }
  }

  const ordem = { ALTA: 0, MEDIA: 1, BAIXA: 2 };
  achados.sort((a, b) => ordem[a.sev] - ordem[b.sev]);
  console.log(`\nTriagem de ${raiz}\n${"=".repeat(60)}`);
  for (const a of achados) console.log(`[${a.sev.padEnd(5)}] ${a.id.padEnd(8)} ${a.file}:${a.line}  ${a.nota}`);
  console.log(`\nTotal: ${achados.length} achados (${achados.filter(a => a.sev === "ALTA").length} de severidade alta)\n`);
}

main();
