package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.OrderItemDto;
import com.ecommerce.backend.model.OrderItem;
import com.ecommerce.backend.repository.OrderItemRepo;
import com.ecommerce.backend.service.serviceImpl.OrderItemImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService implements OrderItemImpl {

  @Autowired
  private OrderItemRepo orderItemRepo;

    private static final Logger logger = LoggerFactory.getLogger(OrderItemService.class);

    @Override
    public Object saveOrUpdateOrderItem(OrderItemDto orderItemDto) {
        if (orderItemDto.getOrderItemId() != null && orderItemRepo.existsById(orderItemDto.getOrderItemId())) {
            OrderItem existingItem = orderItemRepo.findById(orderItemDto.getOrderItemId()).get();
            existingItem.setQuantity(orderItemDto.getQuantity());
            existingItem.setDiscount(orderItemDto.getDiscount());
            existingItem.setOrderedProductPrice(orderItemDto.getOrderedProductPrice());
            orderItemRepo.save(existingItem);
            logger.info("OrderItem updated successfully with ID: {}", orderItemDto.getOrderItemId());
            return "Updated Successfully!!!";
        } else {
            OrderItem newItem = new OrderItem();
            newItem.setQuantity(orderItemDto.getQuantity());
            newItem.setDiscount(orderItemDto.getDiscount());
            newItem.setOrderedProductPrice(orderItemDto.getOrderedProductPrice());
            orderItemRepo.save(newItem);
            logger.info("New OrderItem inserted with ID: {}", newItem.getOrderItemId());
            return "Inserted Successfully!!!";
        }
    }

    @Override
    public List<OrderItemDto> getAllOrderItems() {
        List<OrderItem> items = orderItemRepo.findAll();
        logger.info("Fetched all order items, count: {}", items.size());

        List<OrderItemDto> itemDtos = new ArrayList<>();
        for (OrderItem item : items) {
            OrderItemDto dto = new OrderItemDto();
            dto.setOrderItemId(item.getOrderItemId());
            dto.setQuantity(item.getQuantity());
            dto.setDiscount(item.getDiscount());
            dto.setOrderedProductPrice(item.getOrderedProductPrice());
            itemDtos.add(dto);
        }
        return itemDtos;
    }

    @Override
    public void deleteOrderItemById(Long id) throws Exception {
        logger.info("Delete requested for order item ID: {}", id);
        Optional<OrderItem> optional = orderItemRepo.findById(id);
        if (optional.isPresent()) {
            orderItemRepo.deleteById(id);
            logger.info("OrderItem with ID {} has been deleted successfully.", id);
        } else {
            logger.error("OrderItem not found with ID: {}", id);
            throw new Exception("Id not Found!!!");
        }
    }
}
