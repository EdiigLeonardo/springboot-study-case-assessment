import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, shareReplay } from 'rxjs';
export interface Payment { id:number; externalReference:string; amount:number; status:string; }
@Injectable({providedIn:'root'})
export class PaymentApiService {
 private readonly base='/api/payments';
 // INTENTIONAL BUG: stale forever cache; errors are cached too depending on RxJS config/version semantics.
 payments$=this.http.get<Payment[]>(this.base).pipe(shareReplay(1));
 constructor(private http:HttpClient){}
 create(body:any):Promise<Payment>{ return this.http.post<Payment>(this.base,body).toPromise() as Promise<Payment>; }
 capture(id:number):Observable<Payment>{ return this.http.post<Payment>(`${this.base}/${id}/capture`,{}); }
}
