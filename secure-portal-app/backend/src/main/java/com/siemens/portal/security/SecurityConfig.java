package com.siemens.portal.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;

// BUG: falta @EnableMethodSecurity - os @PreAuthorize nos controllers
// (ver AdminController) sao simplesmente IGNORADOS, sem erro nenhum.
@Configuration
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    // BUG: NoOpPasswordEncoder - passwords guardadas/comparadas em texto puro.
    return NoOpPasswordEncoder.getInstance();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
    http
      // BUG: CSRF desligado globalmente "por ser API" mas a app tambem usa
      // sessao (ver sessionManagement abaixo) - fica vulneravel a CSRF nos
      // endpoints que dependem de cookie de sessao.
      .csrf(csrf -> csrf.disable())
      .sessionManagement(sm -> sm
          .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
          // BUG: desliga a protecao contra session fixation (nao gera nova
          // sessao apos login) - um atacante pode "prender" um session id
          // a vitima antes do login e reutiliza-lo depois.
          .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::none))
      .authorizeHttpRequests(a -> a.requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated())
      // BUG: filtro JWT adicionado DEPOIS do UsernamePasswordAuthenticationFilter
      // em vez de antes -> nalgumas cadeias/pedidos a autenticacao via token
      // nunca chega a ser avaliada a tempo das regras de autorizacao.
      .addFilterAfter(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
