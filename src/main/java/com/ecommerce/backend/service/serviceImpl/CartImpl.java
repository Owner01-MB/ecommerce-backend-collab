package com.ecommerce.backend.service.serviceImpl;

import com.ecommerce.backend.dto.CartDto;
import com.ecommerce.backend.model.Cart;

import java.util.List;

public interface CartImpl {
    Object saveOrUpdateCart(CartDto cartDto);
    List<CartDto> getAllCarts();
    public void deleteCartById(Long id) throws Exception;
}
