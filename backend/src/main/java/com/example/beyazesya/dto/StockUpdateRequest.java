package com.example.beyazesya.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record StockUpdateRequest(
        @NotNull(message = "New stock value is required")
        @PositiveOrZero(message = "Stock cannot be negative")
        Integer newStock
) {
}
