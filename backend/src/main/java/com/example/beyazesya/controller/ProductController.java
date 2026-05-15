package com.example.beyazesya.controller;

import com.example.beyazesya.dto.ProductResponse;
import com.example.beyazesya.dto.ProductStockResponse;
import com.example.beyazesya.dto.StockUpdateRequest;
import com.example.beyazesya.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Integer productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategoryId(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategoryId(categoryId));
    }

    @GetMapping("/stock-view")
    public ResponseEntity<List<ProductStockResponse>> getProductStockView() {
        return ResponseEntity.ok(productService.getProductStockView());
    }

    @PatchMapping("/{productId}/stock")
    public ResponseEntity<ProductStockResponse> updateProductStock(
            @PathVariable Integer productId,
            @Valid @RequestBody StockUpdateRequest request
    ) {
        return ResponseEntity.ok(productService.updateProductStock(productId, request));
    }
}
