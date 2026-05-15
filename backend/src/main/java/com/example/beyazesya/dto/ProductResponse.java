package com.example.beyazesya.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Integer productId,
        Integer categoryId,
        String categoryName,
        String productName,
        String brand,
        BigDecimal price,
        Integer stockQuantity
) {
}
