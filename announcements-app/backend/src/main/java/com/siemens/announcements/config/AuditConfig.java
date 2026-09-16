package com.siemens.announcements.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditConfig {
  // BUG: ignora completamente o utilizador autenticado (SecurityContext) -
  // TODOS os registos ficam sempre com created_by/updated_by = "system".
  @Bean
  public AuditorAware<String> auditorProvider() {
    return () -> Optional.of("system");
  }
}
