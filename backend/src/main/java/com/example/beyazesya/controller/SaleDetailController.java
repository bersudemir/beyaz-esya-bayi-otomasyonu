package com.example.beyazesya.controller;

import com.example.beyazesya.dto.SaleDetailRequest;
import com.example.beyazesya.dto.SaleDetailResponse;
import com.example.beyazesya.response.ApiResponse;
import com.example.beyazesya.service.SaleDetailService;
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
@RequestMapping("/api/sale-details")
@RequiredArgsConstructor
public class SaleDetailController {

    private final SaleDetailService saleDetailService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SaleDetailResponse>>> getAllSaleDetails() {
        return ResponseEntity.ok(ApiResponse.success("Sale details listed successfully", saleDetailService.getAllSaleDetails()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SaleDetailResponse>> getSaleDetailById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success("Sale detail found successfully", saleDetailService.getSaleDetailById(id)));
    }

    @GetMapping("/sale/{saleId}")
    public ResponseEntity<ApiResponse<List<SaleDetailResponse>>> getSaleDetailsBySaleId(@PathVariable Integer saleId) {
        return ResponseEntity.ok(ApiResponse.success("Sale details listed by sale successfully", saleDetailService.getSaleDetailsBySaleId(saleId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SaleDetailResponse>> createSaleDetail(@Valid @RequestBody SaleDetailRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Sale detail created successfully", saleDetailService.createSaleDetail(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SaleDetailResponse>> updateSaleDetail(
            @PathVariable Integer id,
            @Valid @RequestBody SaleDetailRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success("Sale detail updated successfully", saleDetailService.updateSaleDetail(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSaleDetail(@PathVariable Integer id) {
        saleDetailService.deleteSaleDetail(id);
        return ResponseEntity.noContent().build();
    }
}
