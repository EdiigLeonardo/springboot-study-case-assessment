import React, { useState, useEffect } from "react";
import DOMPurify from "dompurify";

const ORIGENS_OK = ["/dashboard", "/perfil", "/definicoes"];

function destinoSeguro(valor) {
  // so caminhos relativos conhecidos: evita javascript:, data:, open redirect
  return ORIGENS_OK.includes(valor) ? valor : "/dashboard";
}

export default function Perfil({ userId }) {
  const [bio, setBio] = useState("");
  const [redirect, setRedirect] = useState("/dashboard");

  useEffect(() => {
    const ac = new AbortController();
    fetch(`https://api.example.com/users/${encodeURIComponent(userId)}`, { signal: ac.signal })
      .then((r) => { if (!r.ok) throw new Error(r.status); return r.json(); })
      .then((d) => {
        // 1a opcao: guardar texto e renderizar com {bio}
        // 2a opcao (se o produto exigir HTML): sanitizar com allow-list
        setBio(DOMPurify.sanitize(String(d.bioHtml ?? ""), {
          ALLOWED_TAGS: ["b", "i", "em", "strong", "p", "br", "ul", "li", "a"],
          ALLOWED_ATTR: ["href"],
        }));
        setRedirect(destinoSeguro(new URLSearchParams(location.search).get("next")));
      })
      .catch(() => setBio(""));
    return () => ac.abort();
  }, [userId]);

  return (
    <div>
      <div dangerouslySetInnerHTML={{ __html: bio }} />
      <a href={redirect}>Continuar</a>
      <img src={`/avatar?u=${encodeURIComponent(userId)}`} alt="" onError={(e) => { e.target.style.display = "none"; }} />
    </div>
  );
}
