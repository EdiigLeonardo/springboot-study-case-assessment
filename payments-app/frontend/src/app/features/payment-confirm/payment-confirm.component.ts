import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-payment-confirm', standalone: true, imports: [CommonModule],
  template: `<input (input)="onCardInput($any($event.target).value)" placeholder="Confirma o numero do cartao" />`,
})
export class PaymentConfirmComponent {
  cardNumber = '';
  // BUG: MESMA logica de mascara copiada de payment-form.component.ts.
  // Se a regra de negocio mudar (ex.: passar a aceitar 19 digitos), tens de
  // lembrar-te de mudar em dois sitios - e um dia vais esquecer-te de um.
  onCardInput(value: string): void {
    const digits = value.replace(/\D/g, '').slice(0, 16);
    this.cardNumber = digits.replace(/(\d{4})(?=\d)/g, '$1 ');
  }
}
