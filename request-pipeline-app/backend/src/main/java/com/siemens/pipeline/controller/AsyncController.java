package com.siemens.pipeline.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.CompletableFuture;

@RestController
public class AsyncController {
  // BUG: com um metodo assincrono, o afterCompletion() dos interceptors
  // (nenhum implementado aqui de proposito) dispara ANTES deste
  // CompletableFuture terminar de verdade - qualquer cleanup/timing que
  // dependa de "afterCompletion == pedido realmente acabou" fica errado.
  @GetMapping("/api/slow")
  public CompletableFuture<String> slow() {
    return CompletableFuture.supplyAsync(() -> {
      try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
      return "pronto";
    });
  }
}
