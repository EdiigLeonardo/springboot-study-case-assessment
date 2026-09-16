package com.siemens.portal.controller;
import com.siemens.portal.repository.UserRepository;
import com.siemens.portal.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController @RequestMapping("/api/auth")
public class AuthController {
  private final UserRepository users;
  private final PasswordEncoder encoder;
  private final JwtService jwt;
  public AuthController(UserRepository u, PasswordEncoder e, JwtService j) { users = u; encoder = e; jwt = j; }

  @PostMapping("/login")
  public String login(@RequestParam String email, @RequestParam String password) {
    var user = users.findByEmail(email).orElseThrow();
    if (!encoder.matches(password, user.getPassword())) throw new RuntimeException("credenciais invalidas");
    return jwt.generate(email);
  }

  // BUG (open redirect): redireciona para o valor de "redirect" sem validar
  // dominio/whitelist - um link tipo /api/auth/callback?redirect=https://evil.com
  // manda a vitima (ja autenticada) para um site malicioso.
  @GetMapping("/callback")
  public RedirectView callback(@RequestParam String redirect) {
    return new RedirectView(redirect);
  }
}
