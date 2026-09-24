package com.acme.payments.repository;
import com.acme.payments.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.Instant;
import java.util.*;
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Optional<Payment> findByExternalReference(String externalReference);
    List<Payment> findByStatus(PaymentStatus status);
    List<Payment> findByStatusAndCreatedAtBefore(PaymentStatus status, Instant before);
}
