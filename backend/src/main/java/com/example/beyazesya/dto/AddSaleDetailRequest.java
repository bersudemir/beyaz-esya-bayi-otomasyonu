package com.example.beyazesya.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddSaleDetailRequest(
        @NotNull(message = "Product id is required")
        @Positive(message = "Product id must be positive")
        Integer productId,

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be positive")
        Integer quantity
) {
}
