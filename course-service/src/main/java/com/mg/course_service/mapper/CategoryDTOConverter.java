package com.mg.course_service.mapper;

import com.mg.course_service.dto.CategoryDTO;
import com.mg.course_service.model.Category;

public class CategoryDTOConverter {
    public static CategoryDTO toDTO(Category category) {
        return new CategoryDTO(category.getMainCategory(), category.getCategory());
    }

    public static Category toEntity(CategoryDTO dto) {
        return new Category(dto.mainCategory(), dto.category());
    }
}
