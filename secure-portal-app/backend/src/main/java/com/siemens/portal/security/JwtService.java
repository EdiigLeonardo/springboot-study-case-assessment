package com.siemens.portal.security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
  // BUG: segredo curto e fixo no codigo -> forca bruta trivial, e nunca
  // muda entre deploys/ambientes (devia vir de fora, com >= 256 bits aleatorios).
  private final SecretKey key = Keys.hmacShaKeyFor("s3cr3t".repeat(4).getBytes());

  public String generate(String email) {
    return Jwts.builder().subject(email)
        .expiration(new Date(System.currentTimeMillis() + 3600_000))
        .signWith(key).compact();
  }

  // BUG: usa parseSignedClaims mas nunca verifica a expiracao explicitamente
  // NEM apanha a excecao de token expirado - um token expirado passa como valido
  // se o chamador so verificar "nao rebentou" em vez de tratar ExpiredJwtException.
  public String extractEmail(String token) {
    try {
      return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    } catch (Exception e) {
      return null; // engole TODAS as excecoes, incluindo token expirado -> trata como anonimo silenciosamente
    }
  }
}
