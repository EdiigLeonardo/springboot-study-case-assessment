// LAB React - encontra e corrige 5 problemas. Solucao em react-corrigido.jsx
import React, { useState, useEffect } from "react";

export default function Perfil({ userId }) {
  const [bio, setBio] = useState("");
  const [redirect, setRedirect] = useState("");

  useEffect(() => {
    fetch(`http://api.example.com/users/${userId}`)
      .then((r) => r.json())
      .then((d) => {
        setBio(d.bioHtml);
        setRedirect(new URLSearchParams(location.search).get("next"));
      });
  }, [userId]);

  const preview = (markdown) => ({ __html: markdown });

  return (
    <div>
      <div dangerouslySetInnerHTML={{ __html: bio }} />
      <a href={redirect}>Continuar</a>
      <img src={`/avatar?u=${userId}`} onError={(e) => (e.target.outerHTML = bio)} />
      <div dangerouslySetInnerHTML={preview(bio)} />
    </div>
  );
}
