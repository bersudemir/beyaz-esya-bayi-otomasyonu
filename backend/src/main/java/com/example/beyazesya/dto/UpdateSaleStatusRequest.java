package com.example.beyazesya.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateSaleStatusRequest(
        @NotBlank(message = "Sale status is required")
        @Pattern(regexp = "Completed|Cancelled", message = "Sale status must be Completed or Cancelled")
        String saleStatus
) {
}
