package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.Category;
import com.ecommerce.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @PostMapping("/saveOrUpdateCategory")
  public ResponseEntity<?> saveOrUpdateCategory(@RequestBody Category category) {
    return new ResponseEntity<>(categoryService.saveOrUpdateCategory(category), HttpStatus.OK);
  }

  @GetMapping("/getAllCategories")
  public ResponseEntity<?> getAllCategory() {
    return new ResponseEntity<>(categoryService.getAllCategory(), HttpStatus.FOUND);
  }

  @PostMapping("/deleteCategory/{id}")
  public void deleteCategoryById(@PathVariable Long id) throws Exception {
    categoryService.deleteCategoryById(id);
  }
}
