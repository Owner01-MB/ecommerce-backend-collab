package com.ecommerce.backend.service.serviceImpl;

import com.ecommerce.backend.dto.OrderDto;
import com.ecommerce.backend.model.Order;

import java.util.List;

public interface OrderImpl {
    Object saveOrUpdateOrder(OrderDto orderDto);
    List<OrderDto> getAllOrders();
    void deleteOrderById(Long id) throws Exception;
}
