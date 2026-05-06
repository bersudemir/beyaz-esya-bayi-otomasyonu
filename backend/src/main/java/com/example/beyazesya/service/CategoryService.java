package com.example.beyazesya.service;

import com.example.beyazesya.dto.CategoryRequest;
import com.example.beyazesya.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Integer id);

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse updateCategory(Integer id, CategoryRequest request);

    void deleteCategory(Integer id);
}
