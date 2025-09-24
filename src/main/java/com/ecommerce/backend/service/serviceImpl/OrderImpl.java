package com.ecommerce.backend.service.serviceImpl;

import com.ecommerce.backend.model.Order;

public interface OrderImpl {
    public Object saveOrUpdateOrder(Order order);
    public Object getAllOrder();
    public void deleteOrderById(Long id) throws Exception;
}
