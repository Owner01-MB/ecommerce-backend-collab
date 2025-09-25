package com.ecommerce.backend.service.serviceImpl;

import com.ecommerce.backend.dto.CartItemDto;
import com.ecommerce.backend.model.CartItem;

import java.util.List;

public interface CartItemImpl {
    Object saveOrUpdateCartItem(CartItemDto cartItemDto);
    List<CartItemDto> getAllCartItems();
    void deleteCartItemById(Long id) throws Exception;
}
