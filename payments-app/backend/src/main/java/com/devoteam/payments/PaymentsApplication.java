package com.devoteam.payments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
@EnableAsync @SpringBootApplication
public class PaymentsApplication {
  public static void main(String[] a) { SpringApplication.run(PaymentsApplication.class, a); }
}
