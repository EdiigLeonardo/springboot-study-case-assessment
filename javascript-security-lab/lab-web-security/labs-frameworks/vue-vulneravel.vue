<!-- LAB Vue 3 - encontra e corrige 5 problemas. Solucao em vue-corrigido.vue -->
<template>
  <div>
    <div v-html="comentario"></div>
    <a :href="link">abrir</a>
    <component :is="tipoVindoDaApi" />
    <iframe :src="previewUrl"></iframe>
    <button @click="executar">correr macro</button>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
const comentario = ref("");
const link = ref("");
const tipoVindoDaApi = ref("");
const previewUrl = ref("");
const macro = ref("");

onMounted(async () => {
  const d = await (await fetch("/api/comentario")).json();
  comentario.value = d.html;
  link.value = d.link;
  tipoVindoDaApi.value = d.componente;
  previewUrl.value = d.preview;
  macro.value = d.macro;
});

function executar() {
  Function(macro.value)();
}
</script>
