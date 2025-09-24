package com.ecommerce.backend.service.serviceImpl;

import com.ecommerce.backend.model.CartItem;

public interface CartItemImpl {
    public Object saveOrUpdateCartItem(CartItem cartItem);
    public Object getAllCartItem();
    public void deleteCartItemById(Long id) throws Exception;
}
