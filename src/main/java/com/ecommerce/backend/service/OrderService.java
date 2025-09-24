package com.ecommerce.backend.service;

import com.ecommerce.backend.model.Order;
import com.ecommerce.backend.repository.OrderRepo;
import com.ecommerce.backend.service.serviceImpl.OrderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService implements OrderImpl {
    @Autowired
    private OrderRepo orderRepo;

    @Override
    public Object saveOrUpdateOrder(Order order) {
        if (order.getOrderId() != null && orderRepo.existsById(order.getOrderId())) {
            Order existingOrder = orderRepo.findById(order.getOrderId()).get();

            existingOrder.setEmail(order.getEmail());
            existingOrder.setOrderDate(order.getOrderDate());
            existingOrder.setTotalAmount(order.getTotalAmount());
            existingOrder.setOrderStatus(order.getOrderStatus());

            orderRepo.save(existingOrder);
            return "Updated Successfully!!!";
        } else {
            orderRepo.save(order);
            return "Inserted Successfully!!!";
        }
    }

    @Override
    public Object getAllOrder() {
        return orderRepo.findAll();
    }

    @Override
    public void deleteOrderById(Long id) throws Exception {
        Optional<Order> optional = orderRepo.findById(id);
        if (optional.isPresent()) {
            orderRepo.deleteById(id);
        } else {
            throw new Exception("Id not Found!!!");
        }
    }
}
