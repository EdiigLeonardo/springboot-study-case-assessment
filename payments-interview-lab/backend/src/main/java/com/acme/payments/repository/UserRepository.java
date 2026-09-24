package com.acme.payments.repository;
import com.acme.payments.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User,Long> {}
