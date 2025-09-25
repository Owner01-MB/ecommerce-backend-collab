package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.CategoryDto;
import com.ecommerce.backend.model.Category;
import com.ecommerce.backend.repository.CategoryRepo;
import com.ecommerce.backend.service.serviceImpl.CategoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements CategoryImpl {

  @Autowired
  private CategoryRepo categoryRepo;

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Override
    public Object saveOrUpdateCategory(CategoryDto categoryDto) {
        if (categoryDto.getCategoryId() != null && categoryRepo.existsById(categoryDto.getCategoryId())) {
            // Update existing category
            Category existingCategory = categoryRepo.findById(categoryDto.getCategoryId()).get();
            existingCategory.setCategoryName(categoryDto.getCategoryName());
            categoryRepo.save(existingCategory);
            logger.info("Category updated successfully with ID: {}", categoryDto.getCategoryId());
            return "Updated Successfully!!!";
        } else {
            // Insert new category
            Category newCategory = new Category();
            newCategory.setCategoryName(categoryDto.getCategoryName());
            categoryRepo.save(newCategory);
            logger.info("New Category inserted with ID: {}", newCategory.getCategoryId());
            return "Inserted Successfully!!!";
        }
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        logger.info("Fetched all categories, count: {}", categories.size());

        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            CategoryDto dto = new CategoryDto();
            dto.setCategoryId(category.getCategoryId());
            dto.setCategoryName(category.getCategoryName());
            categoryDtos.add(dto);
        }
        return categoryDtos;
    }

    @Override
    public void deleteCategoryById(Long id) throws Exception {
        logger.info("Delete requested for category ID: {}", id);
        Optional<Category> optional = categoryRepo.findById(id);
        if (optional.isPresent()) {
            categoryRepo.deleteById(id);
            logger.info("Category with ID {} has been deleted successfully.", id);
        } else {
            logger.error("Category not found with ID: {}", id);
            throw new Exception("Id not Found!!!");
        }
    }
}
