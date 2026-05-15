package com.example.beyazesya.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SaleReportRow(
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
