package com.siemens.hrsync.external;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Component
public class HrClient {
  // BUG: RestTemplate default -> sem connectTimeout/readTimeout (infinito).
  // O timelimiter do yml nunca consegue "cortar" uma chamada que o cliente
  // HTTP nem sequer sabe que devia abandonar.
  private final RestTemplate restTemplate = new RestTemplate();

  // BUG (ordem de composicao): @Retry por fora do @CircuitBreaker. Cada
  // tentativa falhada do Retry conta como uma chamada separada para o
  // CircuitBreaker, abrindo o circuito muito mais cedo do que fazia sentido
  // (ou, dependendo da ordem real de aplicacao dos aspectos, o inverso:
  // o CircuitBreaker abre e o Retry nunca chega a correr). Falta decidir e
  // configurar a ordem explicitamente.
  @Retry(name = "hrClient")
  @CircuitBreaker(name = "hrClient", fallbackMethod = "fallback")
  public List<Employee> fetchEmployees() {
    return List.of(restTemplate.getForObject("https://hr-legacy.example.com/employees", Employee[].class));
  }

  // BUG: assinatura do fallback nao bate certo com o metodo original (falta
  // o parametro Throwable) - o Resilience4j falha a arrancar/associar este
  // fallback, ou ignora-o silenciosamente consoante a versao.
  private List<Employee> fallback() {
    return List.of();
  }

  public record Employee(String id, String name, String email) {}
}
