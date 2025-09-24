package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.Category;
import com.ecommerce.backend.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/saveOrUpdateCategory")
    public ResponseEntity<?> saveOrUpdateCategory(@RequestBody Category category){
        logger.info("Saving or Updating Category: {}", category);
        try {
            return new ResponseEntity<>(categoryService.saveOrUpdateCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while saving/updating category: {}", category, e);
            return new ResponseEntity<>("Failed to save/update category", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<?> getAllCategory(){
        logger.debug("Fetching all categories");
        try {
            return new ResponseEntity<>(categoryService.getAllCategory(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while fetching all categories", e);
            return new ResponseEntity<>("Failed to fetch categories", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteCategory/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) {
        logger.warn("Deleting category with ID: {}", id);
        try {
            categoryService.deleteCategoryById(id);
            return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while deleting category with ID: {}", id, e);
            return new ResponseEntity<>("Failed to delete category", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
