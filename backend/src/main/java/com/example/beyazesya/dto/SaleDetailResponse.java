package com.example.beyazesya.dto;

import java.math.BigDecimal;

public record SaleDetailResponse(
        Integer saleDetailId,
        Integer saleId,
        Integer productId,
        String productName,
        String brand,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal lineTotal
) {
}
