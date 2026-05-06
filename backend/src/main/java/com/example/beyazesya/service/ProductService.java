package com.example.beyazesya.service;

import com.example.beyazesya.dto.ProductRequest;
import com.example.beyazesya.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Integer id);

    List<ProductResponse> getProductsByCategoryId(Integer categoryId);

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(Integer id, ProductRequest request);

    void deleteProduct(Integer id);

    void updateProductStock(Integer productId, Integer newStock);
}
