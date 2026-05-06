package com.example.beyazesya.service.impl;

import com.example.beyazesya.dto.ProductRequest;
import com.example.beyazesya.dto.ProductResponse;
import com.example.beyazesya.entity.Category;
import com.example.beyazesya.entity.Product;
import com.example.beyazesya.exception.ResourceNotFoundException;
import com.example.beyazesya.mapper.ProductMapper;
import com.example.beyazesya.repository.CategoryRepository;
import com.example.beyazesya.repository.ProductRepository;
import com.example.beyazesya.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Integer id) {
        return ProductMapper.toResponse(findProductById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByCategoryId(Integer categoryId) {
        return productRepository.findByCategory_CategoryId(categoryId)
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        Category category = findCategoryById(request.getCategoryId());
        Product product = ProductMapper.toEntity(request, category);
        return ProductMapper.toResponse(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Integer id, ProductRequest request) {
        Product product = findProductById(id);
        Category category = findCategoryById(request.getCategoryId());
        ProductMapper.updateEntity(product, request, category);
        return ProductMapper.toResponse(productRepository.save(product));
    }

    @Override
    @Transactional
    public void deleteProduct(Integer id) {
        Product product = findProductById(id);
        productRepository.delete(product);
    }

    @Override
    @Transactional
    public void updateProductStock(Integer productId, Integer newStock) {
        findProductById(productId);
        productRepository.updateProductStockProcedure(productId, newStock);
    }

    private Product findProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    private Category findCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }
}
