package com.example.beyazesya.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerSaleProcedureResponse {

    private Integer saleId;
    private LocalDateTime saleDate;
    private BigDecimal totalAmount;
    private String saleStatus;
}
