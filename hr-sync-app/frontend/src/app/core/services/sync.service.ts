import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, finalize, timeout } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class SyncService {
  constructor(private http: HttpClient) {}

  // BUG: sem timeout() - se o backend ficar preso (ver bugs do HrClient),
  // o pedido fica pendente indefinidamente e o Observable nunca emite nem erro nem sucesso.
  trigger(): Observable<any> {
    return this.http.post('/api/sync', {});
  }
}
