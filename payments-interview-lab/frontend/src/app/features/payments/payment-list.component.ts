import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { PaymentApiService, Payment } from '../../core/payment-api.service';
import { interval, Subscription } from 'rxjs';
@Component({selector:'app-payment-list',standalone:true,imports:[CommonModule,RouterLink],template:`
<h1>Payments</h1><a routerLink="/payments/new">New payment</a>
<button (click)="reload()">Reload</button>
<div *ngFor="let p of payments">{{p.externalReference}} — {{p.amount}} — {{p.status}} <button (click)="capture(p.id)">Capture</button></div>
`})
export class PaymentListComponent implements OnInit,OnDestroy {
 payments:Payment[]=[];
 private polling?:Subscription;
 constructor(private api:PaymentApiService){}
 ngOnInit(){
  this.api.payments$.subscribe(v=>this.payments=v); // INTENTIONAL BUG: unmanaged subscription
  this.polling=interval(5000).subscribe(()=>this.reload());
 }
 reload(){ this.api.payments$.subscribe(v=>this.payments=v); } // INTENTIONAL BUG: same cached Observable, creates more subscriptions.
 capture(id:number){ this.api.capture(id).subscribe(()=>this.reload()); }
 ngOnDestroy(){ this.polling?.unsubscribe(); /* INTENTIONAL BUG: other subscriptions survive */ }
}
