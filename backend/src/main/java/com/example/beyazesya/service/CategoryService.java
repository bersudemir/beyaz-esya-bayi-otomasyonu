package com.example.beyazesya.service;

import com.example.beyazesya.dto.CategoryRequest;
import com.example.beyazesya.dto.CategoryResponse;
import java.util.List;

public interface CategoryService {

    List<CategoryResponse> getAllCategories();

    CategoryResponse createCategory(CategoryRequest request);
}
