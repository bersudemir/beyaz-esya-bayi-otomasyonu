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
public class SaleDetailResponse {

    private Integer saleDetailId;
    private Integer saleId;
    private Integer productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
}
