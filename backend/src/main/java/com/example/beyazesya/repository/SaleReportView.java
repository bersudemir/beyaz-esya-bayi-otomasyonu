package com.example.beyazesya.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface SaleReportView {

    Integer getSaleId();

    LocalDateTime getSaleDate();

    String getSaleStatus();

    String getCustomerName();

    String getEmployeeName();

    String getProductName();

    Integer getQuantity();

    BigDecimal getUnitPrice();

    BigDecimal getLineTotal();
}
