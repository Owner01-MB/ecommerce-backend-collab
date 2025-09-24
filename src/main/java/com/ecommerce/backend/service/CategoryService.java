package com.ecommerce.backend.service;

import com.ecommerce.backend.model.Category;
import com.ecommerce.backend.repository.CartItemRepo;
import com.ecommerce.backend.repository.CategoryRepo;
import com.ecommerce.backend.service.serviceImpl.CategoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService implements CategoryImpl {

  @Autowired
  private CategoryRepo categoryRepo;

  @Override
  public Object saveOrUpdateCategory(Category category) {
    if (category.getCategoryId() != null && categoryRepo.existsById(category.getCategoryId())) {
      Category existingCategory = categoryRepo.findById(category.getCategoryId()).get();

      existingCategory.setCategoryName(category.getCategoryName());

      categoryRepo.save(existingCategory);
      return "Updated Successfully!!!";
    } else {
      categoryRepo.save(category);
      return "Inserted Successfully!!!";
    }
  }

  @Override
  public Object getAllCategory() {
    return categoryRepo.findAll();
  }

  @Override
  public void deleteCategoryById(Long id) throws Exception {
    Optional<Category> optional = categoryRepo.findById(id);
    if (optional.isPresent()) {
      categoryRepo.deleteById(id);
    } else {
      throw new Exception("Id not Found!!!");
    }
  }
}
