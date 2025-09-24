package com.ecommerce.backend.service.serviceImpl;

import com.ecommerce.backend.model.Category;

public interface CategoryImpl {
    public Object saveOrUpdateCategory(Category category);
    public Object getAllCategory();
    public void deleteCategoryById(Long id) throws Exception;
}
