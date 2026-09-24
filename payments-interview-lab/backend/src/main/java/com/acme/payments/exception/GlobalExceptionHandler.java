package com.acme.payments.exception;

import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> everything(Exception ex){
        // INTENTIONAL BUGS: leaks internal message; swallows type distinction; logs expected errors as full stack traces.
        log.error("Request failed: " + ex.getMessage(), ex);
        return ResponseEntity.status(500).body(Map.of("error", ex.getMessage(), "type", ex.getClass().getName()));
    }
}
