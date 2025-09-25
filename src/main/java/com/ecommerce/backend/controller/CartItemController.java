package com.ecommerce.backend.controller;
import com.ecommerce.backend.dto.CartItemDto;
import com.ecommerce.backend.model.CartItem;
import com.ecommerce.backend.service.CartItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    private static final Logger logger = LoggerFactory.getLogger(CartItemController.class);

    @DeleteMapping("/saveOrUpdateCartItem")
    public ResponseEntity<?> saveOrUpdateCartItem(@RequestBody CartItemDto cartItemDto) {
        logger.info("Received request to saveOrUpdate cart item: {}", cartItemDto);
        try {
            Object response = cartItemService.saveOrUpdateCartItem(cartItemDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while saving/updating cart item: {}", cartItemDto, e);
            return new ResponseEntity<>("Error while saving/updating cart item: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllCartItems")
    public ResponseEntity<?> getAllCartItems() {
        logger.info("Received request to fetch all cart items");
        try {
            List<CartItemDto> items = cartItemService.getAllCartItems();
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while fetching cart items", e);
            return new ResponseEntity<>("Error while fetching cart items: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteCartItem/{id}")
    public ResponseEntity<?> deleteCartItemById(@PathVariable Long id) {
        logger.info("Received request to delete cart item with ID: {}", id);
        try {
            cartItemService.deleteCartItemById(id);
            return new ResponseEntity<>("Deleted Successfully!!!", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while deleting cart item with ID: {}", id, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
