package com.example.beyazesya.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class ProductRequest {

    @NotNull(message = "Category id is required")
    private Integer categoryId;

    @NotBlank(message = "Product name is required")
    @Size(max = 150, message = "Product name can be at most 150 characters")
    private String productName;

    @NotBlank(message = "Brand is required")
    @Size(max = 100, message = "Brand can be at most 100 characters")
    private String brand;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    @Max(value = 1_000_000, message = "Stock quantity is too high")
    private Integer stockQuantity;
}
