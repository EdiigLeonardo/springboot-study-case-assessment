package com.acme.payments.config;

import org.springframework.context.annotation.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {
    // INTENTIONAL BUG: SimpleDateFormat is not thread-safe; singleton bean amplifies the problem.
    @Bean public SimpleDateFormat settlementDateFormat(){ return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); }

    // INTENTIONAL BUG: mutable global singleton state used as an in-memory request cache.
    @Bean public Map<String,Object> globalCache(){ return new HashMap<>(); }
}
