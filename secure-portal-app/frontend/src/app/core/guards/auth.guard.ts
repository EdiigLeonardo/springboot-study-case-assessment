import { CanActivateFn } from '@angular/router';
// BUG: so verifica se existe um token, nunca se ja expirou. Um token
// expirado passa no guard e so falha (com erro pouco claro) no pedido HTTP a seguir.
export const authGuard: CanActivateFn = () => !!localStorage.getItem('token');
