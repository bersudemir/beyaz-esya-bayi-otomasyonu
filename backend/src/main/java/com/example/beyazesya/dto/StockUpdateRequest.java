package com.example.beyazesya.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class StockUpdateRequest {

    @NotNull(message = "New stock is required")
    @Min(value = 0, message = "New stock cannot be negative")
    @Max(value = 1_000_000, message = "New stock is too high")
    private Integer newStock;
}
