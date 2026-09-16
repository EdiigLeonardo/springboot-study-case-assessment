package com.siemens.pipeline.controller;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/echo")
public class EchoController {
  // Chama isto com um corpo JSON e repara que chega vazio (ver BodyLoggingInterceptor).
  @PostMapping public String echo(@RequestBody(required = false) String body) { return "recebi: " + body; }
}
