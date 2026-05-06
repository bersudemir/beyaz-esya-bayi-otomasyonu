package com.example.beyazesya.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name can be at most 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name can be at most 100 characters")
    private String lastName;

    @NotBlank(message = "Phone is required")
    @Size(max = 30, message = "Phone can be at most 30 characters")
    private String phone;

    @NotBlank(message = "Position is required")
    @Size(max = 100, message = "Position can be at most 100 characters")
    private String position;

    @PositiveOrZero(message = "Salary must be zero or positive")
    private BigDecimal salary;
}
