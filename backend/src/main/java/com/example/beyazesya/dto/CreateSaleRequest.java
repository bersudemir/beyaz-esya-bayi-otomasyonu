package com.example.beyazesya.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateSaleRequest(
        @NotNull(message = "Customer id is required")
        @Positive(message = "Customer id must be positive")
        Integer customerId,

        @NotNull(message = "Employee id is required")
        @Positive(message = "Employee id must be positive")
        Integer employeeId
) {
}
