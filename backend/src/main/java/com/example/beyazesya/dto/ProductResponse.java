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
public class ProductResponse {

    private Integer productId;
    private Integer categoryId;
    private String categoryName;
    private String productName;
    private String brand;
    private BigDecimal price;
    private Integer stockQuantity;
}
