package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.Product;
import com.ecommerce.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {


    @Autowired
    private ProductService productService;

    @PostMapping("/saveOrUpdateProduct")
    public ResponseEntity<?> saveOrUpdateProduct(@RequestBody Product product){
        return new ResponseEntity<>(productService.saveOrUpdateProduct(product), HttpStatus.OK);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<?> getAllProduct(){
        return new ResponseEntity<>(productService.getAllProduct(), HttpStatus.FOUND);
    }

    @PostMapping("/deleteProduct/{id}")
    public void deleteProductById(@PathVariable Long id) throws Exception {
        productService.deleteProductById(id);
    }
}
