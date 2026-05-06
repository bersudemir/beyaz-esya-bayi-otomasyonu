package com.example.beyazesya.mapper;

import com.example.beyazesya.dto.CategoryRequest;
import com.example.beyazesya.dto.CategoryResponse;
import com.example.beyazesya.entity.Category;

public final class CategoryMapper {

    private CategoryMapper() {
    }

    public static Category toEntity(CategoryRequest request) {
        return Category.builder()
                .categoryName(request.getCategoryName())
                .build();
    }

    public static void updateEntity(Category category, CategoryRequest request) {
        category.setCategoryName(request.getCategoryName());
    }

    public static CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .build();
    }
}
