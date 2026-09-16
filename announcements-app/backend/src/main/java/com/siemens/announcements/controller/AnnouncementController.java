package com.siemens.announcements.controller;
import com.siemens.announcements.model.Announcement;
import com.siemens.announcements.repository.AnnouncementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/announcements")
public class AnnouncementController {
  private final AnnouncementRepository repository;
  public AnnouncementController(AnnouncementRepository repository) { this.repository = repository; }

  @GetMapping
  public Page<Announcement> list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
    // BUG (off-by-one): "page" vem do frontend como 1-indexado (pagina 1 =
    // primeira), mas PageRequest.of e 0-indexado. Passar "page" direto salta
    // sempre a primeira pagina real.
    // BUG: sem Sort -> Postgres nao garante ordem estavel entre pedidos;
    // paginas seguintes podem repetir ou saltar linhas se houver escritas
    // concorrentes.
    // BUG: devolve sempre Page<> (faz SELECT COUNT(*) em toda a tabela a
    // cada pedido) mesmo quando o frontend so precisa de "mais itens", nunca
    // do total - devia usar Slice<> nesse caso.
    return repository.findAll(PageRequest.of(page, size));
  }
}
