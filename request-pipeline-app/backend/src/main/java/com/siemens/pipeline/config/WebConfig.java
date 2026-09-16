package com.siemens.pipeline.config;
import com.siemens.pipeline.web.AuditInterceptor;
import com.siemens.pipeline.web.BodyLoggingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  private final AuditInterceptor auditInterceptor;
  private final BodyLoggingInterceptor bodyLoggingInterceptor;
  public WebConfig(AuditInterceptor a, BodyLoggingInterceptor b) { auditInterceptor = a; bodyLoggingInterceptor = b; }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // BUG: falta "/**" no fim - so intercepta EXATAMENTE /api/admin, nao
    // /api/admin/users nem nenhuma sub-rota. Toda a arvore fica sem auditoria.
    registry.addInterceptor(auditInterceptor).addPathPatterns("/api/admin");
    registry.addInterceptor(bodyLoggingInterceptor).addPathPatterns("/api/**");
  }
}
