package com.example.beyazesya.dto;

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
public class ProductStockViewResponse {

    private Integer productId;
    private String productName;
    private String categoryName;
    private String brand;
    private BigDecimal price;
    private Integer stockQuantity;
}
