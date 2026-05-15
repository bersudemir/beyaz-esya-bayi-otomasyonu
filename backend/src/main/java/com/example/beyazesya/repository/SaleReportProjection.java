package com.example.beyazesya.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface SaleReportProjection {

    Integer getSaleId();

    LocalDateTime getSaleDate();

    String getSaleStatus();

    BigDecimal getTotalAmount();

    Integer getCustomerId();

    String getCustomerName();

    Integer getEmployeeId();

    String getEmployeeName();

    Integer getProductId();

    String getProductName();

    String getBrand();

    Integer getQuantity();

    BigDecimal getUnitPrice();

    BigDecimal getLineTotal();
}
