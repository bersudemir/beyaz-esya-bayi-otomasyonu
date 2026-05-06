package com.example.beyazesya.controller;

import com.example.beyazesya.dto.CustomerSaleProcedureResponse;
import com.example.beyazesya.repository.CustomerSaleProcedureResult;
import com.example.beyazesya.response.ApiResponse;
import com.example.beyazesya.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/customer-sales/{customerId}")
    public ResponseEntity<ApiResponse<List<CustomerSaleProcedureResponse>>> getCustomerSales(@PathVariable Integer customerId) {
        List<CustomerSaleProcedureResponse> data = reportService.getCustomerSales(customerId)
                .stream()
                .map(this::toCustomerSaleResponse)
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Customer sales report listed successfully", data));
    }

    private CustomerSaleProcedureResponse toCustomerSaleResponse(CustomerSaleProcedureResult result) {
        return CustomerSaleProcedureResponse.builder()
                .saleId(result.getSaleId())
                .saleDate(result.getSaleDate())
                .totalAmount(result.getTotalAmount())
                .saleStatus(result.getSaleStatus())
                .build();
    }
}
