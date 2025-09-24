package com.ecommerce.backend.service;

import com.ecommerce.backend.model.OrderItem;
import com.ecommerce.backend.repository.OrderItemRepo;
import com.ecommerce.backend.repository.OrderRepo;
import com.ecommerce.backend.service.serviceImpl.OrderItemImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderItemService implements OrderItemImpl {

  @Autowired
  private OrderItemRepo orderItemRepo;

  @Override
  public Object saveOrUpdateOrderItem(OrderItem orderItem) {
    if (orderItem.getOrderItemId() != null && orderItemRepo.existsById(
        orderItem.getOrderItemId())) {
      OrderItem existingOrderItem = orderItemRepo.findById(orderItem.getOrderItemId()).get();

      existingOrderItem.setQuantity(orderItem.getQuantity());
      existingOrderItem.setDiscount(orderItem.getDiscount());
      existingOrderItem.setOrderedProductPrice(orderItem.getOrderedProductPrice());

      orderItemRepo.save(existingOrderItem);
      return "Updated Successfully!!!";
    } else {
      orderItemRepo.save(orderItem);
      return "Inserted Successfully!!!";
    }
  }

  @Override
  public Object getAllOrderItem() {
    return orderItemRepo.findAll();
  }

  @Override
  public void deleteOrderItemById(Long id) throws Exception {
    Optional<OrderItem> optional = orderItemRepo.findById(id);
    if (optional.isPresent()) {
      orderItemRepo.deleteById(id);
    } else {
      throw new Exception("Id not Found!!!");
    }
  }
}
