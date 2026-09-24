package com.acme.payments.controller;
import com.acme.payments.dto.*;
import com.acme.payments.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService service;
    public PaymentController(PaymentService service){this.service=service;}
    @PostMapping public ResponseEntity<PaymentResponse> create(@Valid @RequestBody CreatePaymentRequest req){ return ResponseEntity.status(201).body(service.create(req)); }
    @PostMapping("/{id}/capture") public PaymentResponse capture(@PathVariable Long id){ return service.capture(id); }
    @GetMapping public List<PaymentResponse> list(){ return service.list(); }
}
