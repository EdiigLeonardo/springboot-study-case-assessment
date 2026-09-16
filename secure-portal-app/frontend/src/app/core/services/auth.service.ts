import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(private http: HttpClient) {}

  login(email: string, password: string) {
    return this.http.post('/api/auth/login', { email, password }, { responseType: 'text' })
      // BUG: token guardado em localStorage - acessivel a qualquer script
      // (XSS rouba o token diretamente); um httpOnly cookie nao seria legivel por JS.
      .pipe(tap(token => localStorage.setItem('token', token)));
  }

  logout(): void {
    localStorage.removeItem('token');
    // BUG: nao existe listener do evento "storage" - se tiveres a app aberta
    // em 2 separadores, fazer logout num nao desliga o outro (continua a usar o token antigo em memoria/estado).
  }
}
