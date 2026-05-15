package com.example.beyazesya.repository;

import com.example.beyazesya.entity.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

    Optional<Customer> findByPhone(String phone);

    Optional<Customer> findByEmail(String email);
}
