package com.example.beyazesya.repository;

import com.example.beyazesya.entity.Employee;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    boolean existsByPhone(String phone);

    Optional<Employee> findByPhone(String phone);
}
