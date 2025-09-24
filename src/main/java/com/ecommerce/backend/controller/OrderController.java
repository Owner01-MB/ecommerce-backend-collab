package com.ecommerce.backend.controller;
import com.ecommerce.backend.model.Order;
import com.ecommerce.backend.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

        private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

        @Autowired
        private OrderService orderService;

        @PostMapping("/saveOrUpdateOrder")
        public ResponseEntity<?> saveOrUpdateOrder(@RequestBody Order order){
            logger.info("Saving or Updating Order: {}", order);
            try {
                return new ResponseEntity<>(orderService.saveOrUpdateOrder(order), HttpStatus.OK);
            } catch (Exception e) {
                logger.error("Error while saving/updating order: {}", order, e);
                return new ResponseEntity<>("Failed to save/update order", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @GetMapping("/getAllOrders")
        public ResponseEntity<?> getAllOrder(){
            logger.debug("Fetching all orders");
            try {
                return new ResponseEntity<>(orderService.getAllOrder(), HttpStatus.OK);
            } catch (Exception e) {
                logger.error("Error while fetching all orders", e);
                return new ResponseEntity<>("Failed to fetch orders", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PostMapping("/deleteOrder/{id}")
        public ResponseEntity<?> deleteOrderById(@PathVariable Long id) {
            logger.warn("Deleting order with ID: {}", id);
            try {
                orderService.deleteOrderById(id);
                return new ResponseEntity<>("Order deleted successfully", HttpStatus.OK);
            } catch (Exception e) {
                logger.error("Error while deleting order with ID: {}", id, e);
                return new ResponseEntity<>("Failed to delete order", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
}
