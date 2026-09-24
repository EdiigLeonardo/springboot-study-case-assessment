package com.devoteam.payments.controller;
import com.devoteam.payments.model.Customer;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/customers")
public class PaymentController {
  // Chama isto e repara que "Ana" continua duas vezes na resposta (ver
  // Customer.java - falta equals/hashCode).
  @GetMapping("/unique")
  public List<Customer> uniqueCustomers() {
    List<Customer> customers = List.of(new Customer("Ana"), new Customer("Ana"), new Customer("Bruno"));
    return customers.stream().distinct().toList();
  }
}
