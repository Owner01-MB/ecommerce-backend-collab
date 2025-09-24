package com.ecommerce.backend.controller;
import com.ecommerce.backend.model.CartItem;
import com.ecommerce.backend.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @PostMapping("/saveOrUpdateCartItem")
    public ResponseEntity<?> saveOrUpdateCartItem(@RequestBody CartItem cartItem){
        return new ResponseEntity<>(cartItemService.saveOrUpdateCartItem(cartItem), HttpStatus.OK);
    }

    @GetMapping("/getAllCartItems")
    public ResponseEntity<?> getAllCartItem(){
        return new ResponseEntity<>(cartItemService.getAllCartItem(), HttpStatus.FOUND);
    }

    @PostMapping("/deleteCartItem/{id}")
    public void deleteCartItemById(@PathVariable Long id) throws Exception {
        cartItemService.deleteCartItemById(id);
    }
}
