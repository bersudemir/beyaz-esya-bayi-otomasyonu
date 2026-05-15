package com.example.beyazesya.service.impl;

import com.example.beyazesya.dto.ProductResponse;
import com.example.beyazesya.dto.ProductStockResponse;
import com.example.beyazesya.dto.StockUpdateRequest;
import com.example.beyazesya.entity.Category;
import com.example.beyazesya.entity.Product;
import com.example.beyazesya.exception.ResourceNotFoundException;
import com.example.beyazesya.repository.CategoryRepository;
import com.example.beyazesya.repository.ProductProcedureRepository;
import com.example.beyazesya.repository.ProductRepository;
import com.example.beyazesya.repository.ProductStockProjection;
import com.example.beyazesya.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductProcedureRepository productProcedureRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Integer productId) {
        Product product = findProductOrThrow(productId);
        return toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByCategoryId(Integer categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }

        return productRepository.findByCategory_CategoryId(categoryId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductStockResponse> getProductStockView() {
        return productRepository.findProductStockView()
                .stream()
                .map(this::toProductStockResponse)
                .toList();
    }

    @Override
    @Transactional
    public ProductStockResponse updateProductStock(Integer productId, StockUpdateRequest request) {
        findProductOrThrow(productId);

        productProcedureRepository.updateProductStock(productId, request.newStock());

        ProductStockProjection updatedStock = productRepository.findProductStockViewByProductId(productId);
        if (updatedStock == null) {
            throw new ResourceNotFoundException("Product stock view row not found for product id: " + productId);
        }

        return toProductStockResponse(updatedStock);
    }

    private Product findProductOrThrow(Integer productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
    }

    private ProductResponse toResponse(Product product) {
        Category category = product.getCategory();

        return new ProductResponse(
                product.getProductId(),
                category.getCategoryId(),
                category.getCategoryName(),
                product.getProductName(),
                product.getBrand(),
                product.getPrice(),
                product.getStockQuantity()
        );
    }

    private ProductStockResponse toProductStockResponse(ProductStockProjection projection) {
        return new ProductStockResponse(
                projection.getProductId(),
                projection.getProductName(),
                projection.getBrand(),
                projection.getCategoryName(),
                projection.getPrice(),
                projection.getStockQuantity()
        );
    }
}
