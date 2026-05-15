package com.example.beyazesya.dto;

import java.math.BigDecimal;

public record ProductStockResponse(
        Integer productId,
        String productName,
        String brand,
        String categoryName,
        BigDecimal price,
        Integer stockQuantity
) {
}
