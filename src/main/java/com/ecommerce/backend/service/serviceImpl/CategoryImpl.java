package com.ecommerce.backend.service.serviceImpl;

import com.ecommerce.backend.dto.CategoryDto;
import com.ecommerce.backend.model.Category;

import java.util.List;

public interface CategoryImpl {
    Object saveOrUpdateCategory(CategoryDto categoryDto);
    List<CategoryDto> getAllCategories();
    void deleteCategoryById(Long id) throws Exception;
}
