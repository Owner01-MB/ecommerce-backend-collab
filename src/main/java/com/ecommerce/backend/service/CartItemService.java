package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.CartItemDto;
import com.ecommerce.backend.model.CartItem;
import com.ecommerce.backend.repository.CartItemRepo;
import com.ecommerce.backend.service.serviceImpl.CartItemImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService implements CartItemImpl {

    @Autowired
    private CartItemRepo cartItemRepo;

    private static final Logger logger = LoggerFactory.getLogger(CartItemService.class);

    @Override
    public Object saveOrUpdateCartItem(CartItemDto cartItemDto) {
        if (cartItemDto.getCartItemId() != null && cartItemRepo.existsById(cartItemDto.getCartItemId())) {
            // Update existing cart item
            CartItem existingItem = cartItemRepo.findById(cartItemDto.getCartItemId()).get();
            existingItem.setQuantity(cartItemDto.getQuantity());
            existingItem.setDiscount(cartItemDto.getDiscount());
            existingItem.setProductPrice(cartItemDto.getProductPrice());
            cartItemRepo.save(existingItem);
            logger.info("CartItem updated successfully with ID: {}", cartItemDto.getCartItemId());
            return "Updated Successfully!!!";
        } else {
            // Insert new cart item
            CartItem newItem = new CartItem();
            newItem.setQuantity(cartItemDto.getQuantity());
            newItem.setDiscount(cartItemDto.getDiscount());
            newItem.setProductPrice(cartItemDto.getProductPrice());
            cartItemRepo.save(newItem);
            logger.info("New CartItem inserted with ID: {}", newItem.getCartItemId());
            return "Inserted Successfully!!!";
        }
    }

    @Override
    public List<CartItemDto> getAllCartItems() {
        List<CartItem> items = cartItemRepo.findAll();
        logger.info("Fetched all cart items, count: {}", items.size());

        List<CartItemDto> itemDtos = new ArrayList<>();
        for (CartItem item : items) {
            CartItemDto dto = new CartItemDto();
            dto.setCartItemId(item.getCartItemId());
            dto.setQuantity(item.getQuantity());
            dto.setDiscount(item.getDiscount());
            dto.setProductPrice(item.getProductPrice());
            itemDtos.add(dto);
        }
        return itemDtos;
    }

    @Override
    public void deleteCartItemById(Long id) throws Exception {
        logger.info("Delete requested for cart item ID: {}", id);
        Optional<CartItem> optional = cartItemRepo.findById(id);
        if (optional.isPresent()) {
            cartItemRepo.deleteById(id);
            logger.info("CartItem with ID {} has been deleted successfully.", id);
        } else {
            logger.error("CartItem not found with ID: {}", id);
            throw new Exception("Id not Found!!!");
        }
    }
}
