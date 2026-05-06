package com.example.beyazesya.mapper;

import com.example.beyazesya.dto.ProductRequest;
import com.example.beyazesya.dto.ProductResponse;
import com.example.beyazesya.entity.Category;
import com.example.beyazesya.entity.Product;

public final class ProductMapper {

    private ProductMapper() {
    }

    public static Product toEntity(ProductRequest request, Category category) {
        return Product.builder()
                .category(category)
                .productName(request.getProductName())
                .brand(request.getBrand())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .build();
    }

    public static void updateEntity(Product product, ProductRequest request, Category category) {
        product.setCategory(category);
        product.setProductName(request.getProductName());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
    }

    public static ProductResponse toResponse(Product product) {
        Category category = product.getCategory();

        return ProductResponse.builder()
                .productId(product.getProductId())
                .categoryId(category != null ? category.getCategoryId() : null)
                .categoryName(category != null ? category.getCategoryName() : null)
                .productName(product.getProductName())
                .brand(product.getBrand())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .build();
    }
}
