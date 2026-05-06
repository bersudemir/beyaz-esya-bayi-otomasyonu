package com.example.beyazesya.controller;

import com.example.beyazesya.dto.CreateBasicSaleRequest;
import com.example.beyazesya.dto.SaleRequest;
import com.example.beyazesya.dto.SaleResponse;
import com.example.beyazesya.response.ApiResponse;
import com.example.beyazesya.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SaleResponse>>> getAllSales() {
        return ResponseEntity.ok(ApiResponse.success("Sales listed successfully", saleService.getAllSales()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SaleResponse>> getSaleById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success("Sale found successfully", saleService.getSaleById(id)));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<SaleResponse>>> getSalesByCustomerId(@PathVariable Integer customerId) {
        return ResponseEntity.ok(ApiResponse.success("Sales listed by customer successfully", saleService.getSalesByCustomerId(customerId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SaleResponse>> createSale(@Valid @RequestBody SaleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Sale created successfully", saleService.createSale(request)));
    }

    @PostMapping("/create-basic")
    public ResponseEntity<ApiResponse<Void>> createBasicSale(@Valid @RequestBody CreateBasicSaleRequest request) {
        saleService.createBasicSale(request.getCustomerId(), request.getEmployeeId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Basic sale created successfully", null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SaleResponse>> updateSale(
            @PathVariable Integer id,
            @Valid @RequestBody SaleRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success("Sale updated successfully", saleService.updateSale(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Integer id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}
