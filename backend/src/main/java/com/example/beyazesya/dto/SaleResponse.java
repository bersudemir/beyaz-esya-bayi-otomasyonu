package com.example.beyazesya.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SaleResponse(
        Integer saleId,
        Integer customerId,
        String customerName,
        Integer employeeId,
        String employeeName,
        LocalDateTime saleDate,
        BigDecimal totalAmount,
        String saleStatus
) {
}
