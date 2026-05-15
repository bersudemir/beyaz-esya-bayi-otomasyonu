package com.example.beyazesya.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EmployeeResponse(
        Integer employeeId,
        String firstName,
        String lastName,
        String phone,
        String position,
        BigDecimal salary,
        LocalDateTime createdAt
) {
}
