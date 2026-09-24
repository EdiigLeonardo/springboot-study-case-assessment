import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-payment-form', standalone: true, imports: [CommonModule, FormsModule],
  templateUrl: './payment-form.component.html',
  styleUrl: './payment-form.component.scss',
})
export class PaymentFormComponent {
  cardNumber = '';
  // BUG (DRY / reutilizacao - o cenario exato pedido na entrevista): esta
  // logica de mascara existe aqui E outra vez, colada, em
  // payment-confirm.component.ts. Devia ser UM SO directive/pipe partilhado.
  onCardInput(value: string): void {
    const digits = value.replace(/\D/g, '').slice(0, 16);
    this.cardNumber = digits.replace(/(\d{4})(?=\d)/g, '$1 ');
  }
}
