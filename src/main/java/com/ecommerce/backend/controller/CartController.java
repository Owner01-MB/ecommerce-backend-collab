package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.Cart;
import com.ecommerce.backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {


    @Autowired
    private CartService cartService;

    @PostMapping("/saveOrUpdateCart")
    public ResponseEntity<?> saveOrUpdateCart(@RequestBody Cart cart){
        return new ResponseEntity<>(cartService.saveOrUpdateCart(cart), HttpStatus.OK);
    }

    @GetMapping("/getAllCarts")
    public ResponseEntity<?> getAllCart(){
        return new ResponseEntity<>(cartService.getAllCart(), HttpStatus.FOUND);
    }

    @PostMapping("/deleteCart/{id}")
    public void deleteCartById(@PathVariable Long id) throws Exception {
        cartService.deleteCartById(id);
    }
}
