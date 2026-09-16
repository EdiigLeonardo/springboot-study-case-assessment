import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
@Injectable({ providedIn: 'root' })
export class AnnouncementService {
  constructor(private http: HttpClient) {}
  list(page: number, size = 10) {
    // BUG: pede "tudo de uma vez" quando o filtro muda (size=1000) em vez de
    // paginar - anula o proposito da paginacao e sobrecarrega o backend.
    return this.http.get<any>(`/api/announcements?page=${page}&size=${size}`);
  }
}
