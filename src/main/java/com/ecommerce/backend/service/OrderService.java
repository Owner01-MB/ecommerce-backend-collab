package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.OrderDto;
import com.ecommerce.backend.model.Order;
import com.ecommerce.backend.repository.OrderRepo;
import com.ecommerce.backend.service.serviceImpl.OrderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements OrderImpl {

  @Autowired
  private OrderRepo orderRepo;

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Override
    public Object saveOrUpdateOrder(OrderDto orderDto) {
        if (orderDto.getOrderId() != null && orderRepo.existsById(orderDto.getOrderId())) {
            Order existingOrder = orderRepo.findById(orderDto.getOrderId()).get();
            existingOrder.setEmail(orderDto.getEmail());
            existingOrder.setOrderDate(orderDto.getOrderDate());
            existingOrder.setTotalAmount(orderDto.getTotalAmount());
            existingOrder.setOrderStatus(orderDto.getOrderStatus());
            orderRepo.save(existingOrder);
            logger.info("Order updated successfully with ID: {}", orderDto.getOrderId());
            return "Updated Successfully!!!";
        } else {
            Order newOrder = new Order();
            newOrder.setEmail(orderDto.getEmail());
            newOrder.setOrderDate(orderDto.getOrderDate());
            newOrder.setTotalAmount(orderDto.getTotalAmount());
            newOrder.setOrderStatus(orderDto.getOrderStatus());
            orderRepo.save(newOrder);
            logger.info("New Order inserted with ID: {}", newOrder.getOrderId());
            return "Inserted Successfully!!!";
        }
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepo.findAll();
        logger.info("Fetched all orders, count: {}", orders.size());

        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orders) {
            OrderDto dto = new OrderDto();
            dto.setOrderId(order.getOrderId());
            dto.setEmail(order.getEmail());
            dto.setOrderDate(order.getOrderDate());
            dto.setTotalAmount(order.getTotalAmount());
            dto.setOrderStatus(order.getOrderStatus());
            orderDtos.add(dto);
        }
        return orderDtos;
    }

    @Override
    public void deleteOrderById(Long id) throws Exception {
        logger.info("Delete requested for order ID: {}", id);
        Optional<Order> optional = orderRepo.findById(id);
        if (optional.isPresent()) {
            orderRepo.deleteById(id);
            logger.info("Order with ID {} has been deleted successfully.", id);
        } else {
            logger.error("Order not found with ID: {}", id);
            throw new Exception("Id not Found!!!");
        }
    }
}
