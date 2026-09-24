package com.acme.payments.config;

import com.acme.payments.domain.Account;
import com.acme.payments.domain.User;
import com.acme.payments.repository.AccountRepository;
import com.acme.payments.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seed(AccountRepository accounts, UserRepository users) {
        return args -> {
            accounts.save(new Account("PT50000000000000000000001", new BigDecimal("10000.00")));
            accounts.save(new Account("PT50000000000000000000002", new BigDecimal("2500.00")));
            accounts.save(new Account("FR7600000000000000000000001", new BigDecimal("5000.00")));
            users.saveAll(List.of(
                new User("Ana", 30, "ana@example.test"),
                new User("ANA", 26, "ana.duplicate@example.test"),
                new User("Bruno", 30, "bruno@example.test"),
                new User("Claire", 41, "claire@example.test")
            ));
        };
    }
}
