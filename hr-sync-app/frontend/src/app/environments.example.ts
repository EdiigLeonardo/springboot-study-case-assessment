// BUG (Azure): chave real de uma API do Azure escrita aqui. Tudo o que vai
// para "environment.ts" e compilado para o bundle JS e fica PUBLICO no
// browser de qualquer visitante - nao existe "segredo" no frontend.
export const environment = {
  production: true,
  azureMapsKey: 'a1b2c3-real-key-do-not-commit',
  apiUrl: '/api',
};
