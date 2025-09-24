package com.ecommerce.backend.service;

import com.ecommerce.backend.model.CartItem;
import com.ecommerce.backend.repository.CartItemRepo;
import com.ecommerce.backend.service.serviceImpl.CartItemImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemService implements CartItemImpl {
    @Autowired
    private CartItemRepo cartItemRepo;

    @Override
    public Object saveOrUpdateCartItem(CartItem cartItem) {
        if (cartItem.getCartItemId() != null && cartItemRepo.existsById(cartItem.getCartItemId())) {
            CartItem existingCartItem = cartItemRepo.findById(cartItem.getCartItemId()).get();

            existingCartItem.setQuantity(cartItem.getQuantity());
            existingCartItem.setDiscount(cartItem.getDiscount());
            existingCartItem.setProductPrice(cartItem.getProductPrice());

            cartItemRepo.save(existingCartItem);
            return "Updated Successfully!!!";
        } else {
            cartItemRepo.save(cartItem);
            return "Inserted Successfully!!!";
        }
    }

    @Override
    public Object getAllCartItem() {
        return cartItemRepo.findAll();
    }

    @Override
    public void deleteCartItemById(Long id) throws Exception {
        Optional<CartItem> optional = cartItemRepo.findById(id);
        if (optional.isPresent()) {
            cartItemRepo.deleteById(id);
        } else {
            throw new Exception("Id not Found!!!");
        }
    }
}
