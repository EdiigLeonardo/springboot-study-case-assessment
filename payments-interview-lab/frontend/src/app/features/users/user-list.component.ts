import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
@Component({selector:'app-user-list',standalone:true,imports:[CommonModule],template:`<h1>Users age 30</h1><div *ngFor="let u of users">{{u.name}} — {{u.email}}</div>`})
export class UserListComponent implements OnInit {
 users:any[]=[];
 constructor(private http:HttpClient){}
 // INTENTIONAL DESIGN SMELL: component directly owns HTTP endpoint knowledge instead of reusable service/facade.
 ngOnInit(){ this.http.get<any[]>('/api/users/age/30').subscribe(v=>this.users=v); }
}
