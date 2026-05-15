package com.example.beyazesya.repository;

import java.math.BigDecimal;

public interface ProductStockProjection {

    Integer getProductId();

    String getProductName();

    String getBrand();

    String getCategoryName();

    BigDecimal getPrice();

    Integer getStockQuantity();
}
