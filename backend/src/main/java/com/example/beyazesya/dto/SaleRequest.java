package com.example.beyazesya.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleRequest {

    @NotNull(message = "Customer id is required")
    private Integer customerId;

    @NotNull(message = "Employee id is required")
    private Integer employeeId;

    @NotNull(message = "Sale date is required")
    private LocalDateTime saleDate;

    @NotNull(message = "Total amount is required")
    @PositiveOrZero(message = "Total amount must be zero or positive")
    private BigDecimal totalAmount;

    @NotBlank(message = "Sale status is required")
    @Size(max = 50, message = "Sale status can be at most 50 characters")
    private String saleStatus;
}
