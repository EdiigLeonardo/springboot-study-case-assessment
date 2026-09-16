package com.siemens.pipeline.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(c -> c.disable())
        .authorizeHttpRequests(a -> a.requestMatchers("/api/echo/**").permitAll().anyRequest().permitAll());
    return http.build();
  }
  // NOTA: sem @Order nenhum nos Filters proprios (UserContextFilter,
  // CorrelationIdFilter) - a ordem entre eles e em relacao a cadeia do
  // Spring Security fica ao acaso da ordem de registo dos beans.
}
