package com.ecommerce.backend.controller;
import com.ecommerce.backend.dto.OrderItemDto;
import com.ecommerce.backend.model.OrderItem;
import com.ecommerce.backend.service.OrderItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    private static final Logger logger = LoggerFactory.getLogger(OrderItemController.class);

    @PostMapping("/saveOrUpdateOrderItem")
    public ResponseEntity<?> saveOrUpdateOrderItem(@RequestBody OrderItemDto orderItemDto) {
        logger.info("Received request to saveOrUpdate order item: {}", orderItemDto);
        try {
            Object response = orderItemService.saveOrUpdateOrderItem(orderItemDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while saving/updating order item: {}", orderItemDto, e);
            return new ResponseEntity<>("Error while saving/updating order item: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllOrderItems")
    public ResponseEntity<?> getAllOrderItems() {
        logger.info("Received request to fetch all order items");
        try {
            List<OrderItemDto> items = orderItemService.getAllOrderItems();
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while fetching order items", e);
            return new ResponseEntity<>("Error while fetching order items: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteOrderItem/{id}")
    public ResponseEntity<?> deleteOrderItemById(@PathVariable Long id) {
        logger.info("Received request to delete order item with ID: {}", id);
        try {
            orderItemService.deleteOrderItemById(id);
            return new ResponseEntity<>("Deleted Successfully!!!", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while deleting order item with ID: {}", id, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
