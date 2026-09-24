import { Routes } from '@angular/router';
export const routes: Routes = [
 {path:'payments',loadComponent:()=>import('./features/payments/payment-list.component').then(m=>m.PaymentListComponent)},
 {path:'payments/new',loadComponent:()=>import('./features/payments/payment-form.component').then(m=>m.PaymentFormComponent)},
 {path:'users',loadComponent:()=>import('./features/users/user-list.component').then(m=>m.UserListComponent)},
 {path:'',pathMatch:'full',redirectTo:'payments'}
];
