package com.ecommerce.backend.controller;
import com.ecommerce.backend.model.CartItem;
import com.ecommerce.backend.service.CartItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartItemController {

    private static final Logger logger = LoggerFactory.getLogger(CartItemController.class);

    @Autowired
    private CartItemService cartItemService;

    @PostMapping("/saveOrUpdateCartItem")
    public ResponseEntity<?> saveOrUpdateCartItem(@RequestBody CartItem cartItem){
        logger.info("Saving or Updating CartItem: {}", cartItem);
        try {
            return new ResponseEntity<>(cartItemService.saveOrUpdateCartItem(cartItem), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while saving/updating cart item: {}", cartItem, e);
            return new ResponseEntity<>("Failed to save/update cart item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllCartItems")
    public ResponseEntity<?> getAllCartItem(){
        logger.debug("Fetching all cart items");
        try {
            return new ResponseEntity<>(cartItemService.getAllCartItem(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while fetching all cart items", e);
            return new ResponseEntity<>("Failed to fetch cart items", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteCartItem/{id}")
    public ResponseEntity<?> deleteCartItemById(@PathVariable Long id) {
        logger.warn("Deleting cart item with ID: {}", id);
        try {
            cartItemService.deleteCartItemById(id);
            return new ResponseEntity<>("Cart item deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while deleting cart item with ID: {}", id, e);
            return new ResponseEntity<>("Failed to delete cart item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
