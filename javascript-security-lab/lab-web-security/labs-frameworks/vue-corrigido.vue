<template>
  <div>
    <!-- texto por omissao; v-html so com HTML sanitizado -->
    <p>{{ comentario }}</p>
    <a v-if="link" :href="link" rel="noopener noreferrer" target="_blank">abrir</a>
    <component :is="componenteSeguro" />
    <iframe v-if="previewUrl" :src="previewUrl" sandbox="allow-scripts" referrerpolicy="no-referrer"></iframe>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import CartaoA from "./CartaoA.vue";
import CartaoB from "./CartaoB.vue";

const COMPONENTES = { cartaoA: CartaoA, cartaoB: CartaoB };
const comentario = ref("");
const link = ref("");
const tipo = ref("");
const previewUrl = ref("");

const componenteSeguro = computed(() => COMPONENTES[tipo.value] ?? null);

function urlSegura(valor) {
  try {
    const u = new URL(valor, location.origin);
    return ["http:", "https:"].includes(u.protocol) ? u.href : "";
  } catch {
    return "";
  }
}

onMounted(async () => {
  const r = await fetch("/api/comentario");
  if (!r.ok) return;
  const d = await r.json();
  comentario.value = String(d.texto ?? "");   // API devolve texto, nao HTML
  link.value = urlSegura(d.link ?? "");
  tipo.value = String(d.componente ?? "");
  previewUrl.value = urlSegura(d.preview ?? "");
  // macros vindas do servidor foram removidas: nada de Function()/eval no cliente
});
</script>
