package com.example.beyazesya.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SaleReportResponse(
        Integer saleId,
        LocalDateTime saleDate,
        String saleStatus,
        BigDecimal totalAmount,
        Integer customerId,
        String customerName,
        Integer employeeId,
        String employeeName,
        Integer productId,
        String productName,
        String brand,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal lineTotal
) {
}
