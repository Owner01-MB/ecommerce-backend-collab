package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.OrderItem;
import com.ecommerce.backend.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderItemController {

  @Autowired
  private OrderItemService orderItemService;

  @PostMapping("/saveOrUpdateOrderItem")
  public ResponseEntity<?> saveOrUpdateOrderItem(@RequestBody OrderItem orderItem) {
    return new ResponseEntity<>(orderItemService.saveOrUpdateOrderItem(orderItem), HttpStatus.OK);
  }

  @GetMapping("/getAllOrderItems")
  public ResponseEntity<?> getAllOrderItem() {
    return new ResponseEntity<>(orderItemService.getAllOrderItem(), HttpStatus.FOUND);
  }

  @PostMapping("/deleteOrderItem/{id}")
  public void deleteOrderItemById(@PathVariable Long id) throws Exception {
    orderItemService.deleteOrderItemById(id);
  }
}
