package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.CartDto;
import com.ecommerce.backend.model.Cart;
import com.ecommerce.backend.repository.CartRepo;
import com.ecommerce.backend.service.serviceImpl.CartImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements CartImpl {

    @Autowired
    private CartRepo cartRepo;

    Logger logger = LoggerFactory.getLogger(CartService.class);

    @Override
    public Object saveOrUpdateCart(CartDto cartDto) {
        if (cartDto.getCartId() != null && cartRepo.existsById(cartDto.getCartId())) {
            // Update existing cart
            Cart existingCart = cartRepo.findById(cartDto.getCartId()).get();
            existingCart.setTotalPrice(cartDto.getTotalPrice());
            cartRepo.save(existingCart);
            logger.info("Cart updated successfully with ID: {}", cartDto.getCartId());
            return "Updated Successfully!!!";
        } else {
            // Insert new cart
            Cart newCart = new Cart();
            newCart.setTotalPrice(cartDto.getTotalPrice());
            cartRepo.save(newCart);
            logger.info("New cart inserted with ID: {}", newCart.getCartId());
            return "Inserted Successfully!!!";
        }
    }

    @Override
    public List<CartDto> getAllCarts() {
        List<Cart> carts = cartRepo.findAll();
        logger.info("Fetched all carts, count: {}", carts.size());

        List<CartDto> cartDtos = new ArrayList<>();
        for (Cart cart : carts) {
            CartDto dto = new CartDto();
            dto.setCartId(cart.getCartId());
            dto.setTotalPrice(cart.getTotalPrice());
            cartDtos.add(dto);
        }
        return cartDtos;
    }

    @Override
    public void deleteCartById(Long id) throws Exception {
        try {
            Optional<Cart> optional = cartRepo.findById(id);
            if (optional.isPresent()) {
                cartRepo.deleteById(id);
                logger.info("Cart with ID {} has been deleted successfully.", id);
            } else {
                logger.error("Cart not found with ID: {}", id);
                throw new Exception("Id not Found!!!");
            }
        } catch (Exception e) {
            logger.error("Error while deleting Cart with ID: {}", id, e);
            throw e;
        }
    }
}
