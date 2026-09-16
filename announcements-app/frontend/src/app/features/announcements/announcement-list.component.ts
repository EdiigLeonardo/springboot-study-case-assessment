import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AnnouncementService } from '../../core/services/announcement.service';

@Component({
  selector: 'app-announcement-list', standalone: true, imports: [CommonModule],
  templateUrl: './announcement-list.component.html',
})
export class AnnouncementListComponent {
  page = 1; // UI mostra "pagina 1" - condiz com o bug off-by-one do backend.
  totalItems = 0;
  pageSize = 10;
  cache: Record<number, any[]> = {}; // BUG: cache por numero de pagina sem chave de filtro.
  items: any[] = [];

  constructor(private service: AnnouncementService) { this.load(); }

  get totalPages(): number {
    // BUG: Math.floor em vez de Math.ceil - a ultima pagina parcial desaparece.
    return Math.floor(this.totalItems / this.pageSize);
  }

  load(): void {
    if (this.cache[this.page]) { this.items = this.cache[this.page]; return; } // devolve cache "errado" apos mudar filtro (nao existe filtro aqui, mas o padrao e o bug a evitar)
    this.service.list(this.page, this.pageSize).subscribe(res => {
      this.items = res.content;
      this.totalItems = res.totalElements;
      this.cache[this.page] = res.content;
    });
  }

  next(): void { this.page++; this.load(); }
}
