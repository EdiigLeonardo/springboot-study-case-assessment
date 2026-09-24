import { Injectable } from '@angular/core';
import { interval } from 'rxjs';
import { firstValueFrom } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class PaymentStatusService {
  private status$ = interval(2000); // simula um Observable que emite VARIAS vezes (polling de status)

  // BUG (Observable vs Promise): firstValueFrom converte para Promise e
  // resolve com o PRIMEIRO valor, cancelando a subscricao logo a seguir.
  // Quem chama isto a espera de "atualizacoes continuas do estado do
  // pagamento" so recebe UMA, nunca mais nada.
  async waitForUpdate(): Promise<number> {
    return firstValueFrom(this.status$);
  }
}
