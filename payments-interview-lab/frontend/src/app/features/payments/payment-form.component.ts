import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { PaymentApiService } from '../../core/payment-api.service';
import { cardNumberValidator } from '../../shared/card.validator';
import { CardMaskDirective } from '../../shared/card-mask.directive';
@Component({selector:'app-payment-form',standalone:true,imports:[CommonModule,ReactiveFormsModule,MatFormFieldModule,MatInputModule,MatButtonModule,CardMaskDirective],template:`
<h1>New payment</h1>
<form [formGroup]="form" (ngSubmit)="submit()">
 <mat-form-field><mat-label>Reference</mat-label><input matInput formControlName="externalReference"></mat-form-field>
 <mat-form-field><mat-label>Merchant</mat-label><input matInput formControlName="merchantId"></mat-form-field>
 <mat-form-field><mat-label>Payer IBAN</mat-label><input matInput formControlName="payerIban"></mat-form-field>
 <mat-form-field><mat-label>Payee IBAN</mat-label><input matInput formControlName="payeeIban"></mat-form-field>
 <mat-form-field><mat-label>Amount</mat-label><input matInput type="number" formControlName="amount"></mat-form-field>
 <mat-form-field><mat-label>Card</mat-label><input matInput cardMask formControlName="cardNumber"></mat-form-field>
 <button mat-raised-button type="submit">Pay</button>
</form><p>{{message}}</p>
`})
export class PaymentFormComponent {
 message='';
 form=this.fb.group({
  externalReference:['',Validators.required], merchantId:['',Validators.required], payerIban:['',Validators.required], payeeIban:['',Validators.required],
  amount:[0,[Validators.required,Validators.min(0)]], // INTENTIONAL BUG: 0 allowed by min(0), backend requires >=0.01.
  cardNumber:['',[Validators.required,cardNumberValidator()]]
 });
 constructor(private fb:FormBuilder,private api:PaymentApiService){}
 async submit(){
  // INTENTIONAL BUGS: no markAllAsTouched, sends invalid form, Promise conversion removes cancellation/composition advantages.
  try { const result=await this.api.create(this.form.value); this.message=`Created ${result.id}`; }
  catch(e:any){ this.message=e?.error?.error ?? e.message; }
 }
}
