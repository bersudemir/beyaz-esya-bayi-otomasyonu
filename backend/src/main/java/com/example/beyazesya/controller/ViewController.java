package com.example.beyazesya.controller;

import com.example.beyazesya.dto.ProductStockViewResponse;
import com.example.beyazesya.dto.SaleReportViewResponse;
import com.example.beyazesya.entity.ProductStockView;
import com.example.beyazesya.repository.SaleReportView;
import com.example.beyazesya.response.ApiResponse;
import com.example.beyazesya.service.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/views")
@RequiredArgsConstructor
public class ViewController {

    private final ViewService viewService;

    @GetMapping("/product-stock")
    public ResponseEntity<ApiResponse<List<ProductStockViewResponse>>> getProductStockView() {
        List<ProductStockViewResponse> data = viewService.getProductStockView()
                .stream()
                .map(this::toProductStockResponse)
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Product stock view listed successfully", data));
    }

    @GetMapping("/sale-report")
    public ResponseEntity<ApiResponse<List<SaleReportViewResponse>>> getSaleReportView() {
        List<SaleReportViewResponse> data = viewService.getSaleReportView()
                .stream()
                .map(this::toSaleReportResponse)
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Sale report view listed successfully", data));
    }

    private ProductStockViewResponse toProductStockResponse(ProductStockView view) {
        return ProductStockViewResponse.builder()
                .productId(view.getProductId())
                .productName(view.getProductName())
                .categoryName(view.getCategoryName())
                .brand(view.getBrand())
                .price(view.getPrice())
                .stockQuantity(view.getStockQuantity())
                .build();
    }

    private SaleReportViewResponse toSaleReportResponse(SaleReportView view) {
        return SaleReportViewResponse.builder()
                .saleId(view.getSaleId())
                .saleDate(view.getSaleDate())
                .saleStatus(view.getSaleStatus())
                .customerName(view.getCustomerName())
                .employeeName(view.getEmployeeName())
                .productName(view.getProductName())
                .quantity(view.getQuantity())
                .unitPrice(view.getUnitPrice())
                .lineTotal(view.getLineTotal())
                .build();
    }
}
