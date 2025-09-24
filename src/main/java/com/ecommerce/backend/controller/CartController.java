package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.Cart;
import com.ecommerce.backend.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @PostMapping("/saveOrUpdateCart")
    public ResponseEntity<?> saveOrUpdateCart(@RequestBody Cart cart){
        logger.info("Saving or Updating Cart: {}", cart);
        try {
            return new ResponseEntity<>(cartService.saveOrUpdateCart(cart), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while saving cart: {}", cart, e);
            return new ResponseEntity<>("Failed to save cart", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllCarts")
    public ResponseEntity<?> getAllCart(){
        logger.debug("Fetching all carts");
        try {
            return new ResponseEntity<>(cartService.getAllCart(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while fetching all carts", e);
            return new ResponseEntity<>("Failed to fetch carts", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteCart/{id}")
    public ResponseEntity<?> deleteCartById(@PathVariable Long id) {
        logger.warn("Deleting cart with ID: {}", id);
        try {
            cartService.deleteCartById(id);
            return new ResponseEntity<>("Cart deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while deleting cart with ID: {}", id, e);
            return new ResponseEntity<>("Failed to delete cart", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
