import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SyncService } from '../../core/services/sync.service';

@Component({
  selector: 'app-sync-panel', standalone: true, imports: [CommonModule],
  template: `<button (click)="run()" [disabled]="loading">Sincronizar</button><p *ngIf="loading">A sincronizar...</p>`,
})
export class SyncPanelComponent {
  loading = false;
  private pollHandle: any;

  constructor(private syncService: SyncService) {}

  run(): void {
    this.loading = true;
    // BUG: sem finalize() - se o pedido der ERRO, "loading" nunca volta a
    // false (so o "next" desliga o spinner); a UI fica presa em "A sincronizar...".
    this.syncService.trigger().subscribe(() => (this.loading = false));

    // BUG: "retry" feito a mao com setInterval em vez de operadores RxJS
    // (retry/timer) - reinventa mal algo que o RxJS ja resolve, sem
    // backoff, sem cancelamento limpo, dificil de testar.
    this.pollHandle = setInterval(() => {
      this.syncService.trigger().subscribe();
    }, 3000);
  }
}
