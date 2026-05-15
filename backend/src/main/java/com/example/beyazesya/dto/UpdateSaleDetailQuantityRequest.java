package com.example.beyazesya.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateSaleDetailQuantityRequest(
        @NotNull(message = "New quantity is required")
        @Positive(message = "New quantity must be positive")
        Integer newQuantity
) {
}
