import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
@Component({selector:'app-root',standalone:true,imports:[RouterLink,RouterOutlet],template:`<nav><a routerLink="/payments">Payments</a> | <a routerLink="/users">Users</a></nav><router-outlet/>`})
export class AppComponent {}
