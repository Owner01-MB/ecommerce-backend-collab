package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.CartDto;
import com.ecommerce.backend.model.Cart;
import com.ecommerce.backend.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @PostMapping("/saveOrUpdateCart")
    public ResponseEntity<?> saveOrUpdateCart(@RequestBody CartDto cartDto) {
        logger.info("Received request to saveOrUpdate cart: {}", cartDto);
        try {
            Object response = cartService.saveOrUpdateCart(cartDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while saving/updating cart: {}", cartDto, e);
            return new ResponseEntity<>("Error while saving/updating cart: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllCarts")
    public ResponseEntity<?> getAllCarts() {
        logger.info("Received request to fetch all carts");
        try {
            List<CartDto> carts = cartService.getAllCarts();
            return new ResponseEntity<>(carts, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while fetching carts", e);
            return new ResponseEntity<>("Error while fetching carts: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteCart/{id}")
    public ResponseEntity<?> deleteCartById(@PathVariable Long id) {
        logger.info("Received request to delete cart with ID: {}", id);
        try {
            cartService.deleteCartById(id);
            logger.info("Deleted cart successfully with ID: {}", id);
            return new ResponseEntity<>("Deleted Successfully!!!", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while deleting cart with ID: {}", id, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
