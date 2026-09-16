package com.siemens.pipeline.web;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.io.ByteArrayOutputStream;

@Component
public class BodyLoggingInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
    // BUG: le o InputStream diretamente para logar o corpo. Um InputStream
    // so pode ser lido UMA VEZ - o @RequestBody do controller (lido depois,
    // no Spring MVC) recebe um stream ja vazio/consumido.
    var out = new ByteArrayOutputStream();
    req.getInputStream().transferTo(out);
    System.out.println("BODY: " + out);
    return true;
  }
}
