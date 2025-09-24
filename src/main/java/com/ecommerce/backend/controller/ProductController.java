package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.Product;
import com.ecommerce.backend.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @PostMapping("/saveOrUpdateProduct")
    public ResponseEntity<?> saveOrUpdateProduct(@RequestBody Product product){
        logger.info("Saving or Updating Product: {}", product);
        try {
            return new ResponseEntity<>(productService.saveOrUpdateProduct(product), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while saving/updating product: {}", product, e);
            return new ResponseEntity<>("Failed to save/update product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<?> getAllProduct(){
        logger.debug("Fetching all products");
        try {
            return new ResponseEntity<>(productService.getAllProduct(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while fetching all products", e);
            return new ResponseEntity<>("Failed to fetch products", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteProduct/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
        logger.warn("Deleting product with ID: {}", id);
        try {
            productService.deleteProductById(id);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while deleting product with ID: {}", id, e);
            return new ResponseEntity<>("Failed to delete product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
