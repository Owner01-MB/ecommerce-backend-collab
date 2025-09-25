package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.CategoryDto;
import com.ecommerce.backend.model.Category;
import com.ecommerce.backend.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @PostMapping("/saveOrUpdateCategory")
    public ResponseEntity<?> saveOrUpdateCategory(@RequestBody CategoryDto categoryDto) {
        logger.info("Received request to saveOrUpdate category: {}", categoryDto);
        try {
            Object response = categoryService.saveOrUpdateCategory(categoryDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while saving/updating category: {}", categoryDto, e);
            return new ResponseEntity<>("Error while saving/updating category: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<?> getAllCategories() {
        logger.info("Received request to fetch all categories");
        try {
            List<CategoryDto> categories = categoryService.getAllCategories();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while fetching categories", e);
            return new ResponseEntity<>("Error while fetching categories: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) {
        logger.info("Received request to delete category with ID: {}", id);
        try {
            categoryService.deleteCategoryById(id);
            return new ResponseEntity<>("Deleted Successfully!!!", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while deleting category with ID: {}", id, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
