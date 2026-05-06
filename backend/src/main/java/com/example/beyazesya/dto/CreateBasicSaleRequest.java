package com.example.beyazesya.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBasicSaleRequest {

    @NotNull(message = "Customer id is required")
    private Integer customerId;

    @NotNull(message = "Employee id is required")
    private Integer employeeId;
}
