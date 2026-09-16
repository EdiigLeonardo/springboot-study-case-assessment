package com.siemens.portal.controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/admin")
public class AdminController {
  // Anotacao existe, mas sem @EnableMethodSecurity (ver SecurityConfig) nunca e avaliada.
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/users")
  public String listUsers() { return "lista secreta de utilizadores"; }
}
