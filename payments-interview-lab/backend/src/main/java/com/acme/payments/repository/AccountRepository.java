package com.acme.payments.repository;
import com.acme.payments.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface AccountRepository extends JpaRepository<Account,Long> { Optional<Account> findByIban(String iban); }
