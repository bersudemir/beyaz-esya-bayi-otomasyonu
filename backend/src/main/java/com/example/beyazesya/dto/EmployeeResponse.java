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
public class EmployeeResponse {

    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String phone;
    private String position;
    private BigDecimal salary;
    private LocalDateTime createdAt;
}
