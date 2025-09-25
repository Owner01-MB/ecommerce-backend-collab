package com.ecommerce.backend.controller;
import com.ecommerce.backend.dto.OrderDto;
import com.ecommerce.backend.model.Order;
import com.ecommerce.backend.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping("/saveOrUpdateOrder")
    public ResponseEntity<?> saveOrUpdateOrder(@RequestBody OrderDto orderDto) {
        logger.info("Received request to saveOrUpdate order: {}", orderDto);
        try {
            Object response = orderService.saveOrUpdateOrder(orderDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while saving/updating order: {}", orderDto, e);
            return new ResponseEntity<>("Error while saving/updating order: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<?> getAllOrders() {
        logger.info("Received request to fetch all orders");
        try {
            List<OrderDto> orders = orderService.getAllOrders();
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while fetching orders", e);
            return new ResponseEntity<>("Error while fetching orders: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/deleteOrder/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable Long id) {
        logger.info("Received request to delete order with ID: {}", id);
        try {
            orderService.deleteOrderById(id);
            return new ResponseEntity<>("Deleted Successfully!!!", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while deleting order with ID: {}", id, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
