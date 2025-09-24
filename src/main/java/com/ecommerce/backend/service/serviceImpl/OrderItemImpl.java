package com.ecommerce.backend.service.serviceImpl;

import com.ecommerce.backend.model.OrderItem;

public interface OrderItemImpl {
    public Object saveOrUpdateOrderItem(OrderItem orderItem);
    public Object getAllOrderItem();
    public void deleteOrderItemById(Long id) throws Exception;
}
