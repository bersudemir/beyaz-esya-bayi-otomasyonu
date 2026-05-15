package com.example.beyazesya.controller;

import com.example.beyazesya.dto.AddSaleDetailRequest;
import com.example.beyazesya.dto.CreateSaleRequest;
import com.example.beyazesya.dto.SaleReportResponse;
import com.example.beyazesya.dto.SaleResponse;
import com.example.beyazesya.dto.UpdateSaleDetailQuantityRequest;
import com.example.beyazesya.dto.UpdateSaleStatusRequest;
import com.example.beyazesya.service.SaleService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<SaleResponse> createSale(@Valid @RequestBody CreateSaleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saleService.createSale(request));
    }

    @PostMapping("/{saleId}/details")
    public ResponseEntity<SaleResponse> addSaleDetail(
            @PathVariable Integer saleId,
            @Valid @RequestBody AddSaleDetailRequest request
    ) {
        return ResponseEntity.ok(saleService.addSaleDetail(saleId, request));
    }

    @PutMapping("/{saleId}/details/{productId}")
    public ResponseEntity<SaleResponse> updateSaleDetailQuantity(
            @PathVariable Integer saleId,
            @PathVariable Integer productId,
            @Valid @RequestBody UpdateSaleDetailQuantityRequest request
    ) {
        return ResponseEntity.ok(saleService.updateSaleDetailQuantity(saleId, productId, request));
    }

    @PatchMapping("/{saleId}/status")
    public ResponseEntity<SaleResponse> updateSaleStatus(
            @PathVariable Integer saleId,
            @Valid @RequestBody UpdateSaleStatusRequest request
    ) {
        return ResponseEntity.ok(saleService.updateSaleStatus(saleId, request));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<SaleReportResponse>> getCustomerSales(@PathVariable Integer customerId) {
        return ResponseEntity.ok(saleService.getCustomerSales(customerId));
    }

    @GetMapping("/report")
    public ResponseEntity<List<SaleReportResponse>> getSaleReport() {
        return ResponseEntity.ok(saleService.getSaleReport());
    }
}
