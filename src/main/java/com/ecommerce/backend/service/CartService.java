package com.ecommerce.backend.service;

import com.ecommerce.backend.model.Cart;
import com.ecommerce.backend.repository.CartRepo;
import com.ecommerce.backend.service.serviceImpl.CartImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService implements CartImpl {
    @Autowired
    private CartRepo cartRepo;


    @Override
    public Object saveOrUpdateCart(Cart cart) {
        if (cart.getCartId() != null && cartRepo.existsById(cart.getCartId())) {
            Cart existingCart = cartRepo.findById(cart.getCartId()).get();

            existingCart.setTotalPrice(cart.getTotalPrice());

            cartRepo.save(existingCart);
            return "Updated Successfully!!!";
        } else {
            cartRepo.save(cart);
            return "Inserted Successfully!!!";
        }
    }

    @Override
    public Object getAllCart() {
        return cartRepo.findAll();
    }

    @Override
    public void deleteCartById(Long id) throws Exception {
        Optional<Cart> optional = cartRepo.findById(id);
        if (optional.isPresent()) {
            cartRepo.deleteById(id);
        } else {
            throw new Exception("Id not Found!!!");
        }
    }
}
