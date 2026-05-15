package com.example.beyazesya.service;

import com.example.beyazesya.dto.ProductResponse;
import com.example.beyazesya.dto.ProductStockResponse;
import com.example.beyazesya.dto.StockUpdateRequest;
import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Integer productId);

    List<ProductResponse> getProductsByCategoryId(Integer categoryId);

    List<ProductStockResponse> getProductStockView();

    ProductStockResponse updateProductStock(Integer productId, StockUpdateRequest request);
}
