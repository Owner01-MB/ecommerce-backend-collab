package com.ecommerce.backend.service.serviceImpl;

import com.ecommerce.backend.model.Cart;

public interface CartImpl {
    public Object saveOrUpdateCart(Cart cart);
    public Object getAllCart();
    public void deleteCartById(Long id) throws Exception;
}
