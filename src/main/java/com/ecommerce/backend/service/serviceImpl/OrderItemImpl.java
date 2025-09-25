package com.ecommerce.backend.service.serviceImpl;

import com.ecommerce.backend.dto.OrderItemDto;
import com.ecommerce.backend.model.OrderItem;

import java.util.List;

public interface OrderItemImpl {
    Object saveOrUpdateOrderItem(OrderItemDto orderItemDto);
    List<OrderItemDto> getAllOrderItems();
    void deleteOrderItemById(Long id) throws Exception;
}
