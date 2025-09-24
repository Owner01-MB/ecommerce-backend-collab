package com.ecommerce.backend.controller;
import com.ecommerce.backend.model.OrderItem;
import com.ecommerce.backend.service.OrderItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderItemController {

    private static final Logger logger = LoggerFactory.getLogger(OrderItemController.class);

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping("/saveOrUpdateOrderItem")
    public ResponseEntity<?> saveOrUpdateOrderItem(@RequestBody OrderItem orderItem){
        logger.info("Saving or Updating OrderItem: {}", orderItem);
        try {
            return new ResponseEntity<>(orderItemService.saveOrUpdateOrderItem(orderItem), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while saving/updating order item: {}", orderItem, e);
            return new ResponseEntity<>("Failed to save/update order item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllOrderItems")
    public ResponseEntity<?> getAllOrderItem(){
        logger.debug("Fetching all order items");
        try {
            return new ResponseEntity<>(orderItemService.getAllOrderItem(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while fetching all order items", e);
            return new ResponseEntity<>("Failed to fetch order items", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteOrderItem/{id}")
    public ResponseEntity<?> deleteOrderItemById(@PathVariable Long id) {
        logger.warn("Deleting order item with ID: {}", id);
        try {
            orderItemService.deleteOrderItemById(id);
            return new ResponseEntity<>("Order item deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while deleting order item with ID: {}", id, e);
            return new ResponseEntity<>("Failed to delete order item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
