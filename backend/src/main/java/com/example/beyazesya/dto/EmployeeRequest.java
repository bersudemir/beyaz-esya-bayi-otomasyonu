package com.example.beyazesya.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record EmployeeRequest(
        @NotBlank(message = "First name is required")
        @Size(max = 50, message = "First name can be at most 50 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 50, message = "Last name can be at most 50 characters")
        String lastName,

        @NotBlank(message = "Phone is required")
        @Size(max = 20, message = "Phone can be at most 20 characters")
        String phone,

        @NotBlank(message = "Position is required")
        @Size(max = 50, message = "Position can be at most 50 characters")
        String position,

        @NotNull(message = "Salary is required")
        @PositiveOrZero(message = "Salary cannot be negative")
        BigDecimal salary
) {
}
