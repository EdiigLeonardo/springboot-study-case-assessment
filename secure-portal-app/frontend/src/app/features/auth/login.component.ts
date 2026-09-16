import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-login', standalone: true,
  template: `<button (click)="doAdminAction()">Ação de admin</button>`,
})
export class LoginComponent {
  constructor(private http: HttpClient, private auth: AuthService) {}

  // BUG: pedido POST/estado a mudar sem cabeçalho CSRF - se o backend usa
  // protecao CSRF baseada em cookie (XSRF-TOKEN), este pedido e rejeitado
  // (ou, pior, se a protecao estiver mal configurada no backend, passa sem controlo nenhum).
  doAdminAction(): void {
    this.http.post('/api/admin/users/promote', {}).subscribe();
  }
}
