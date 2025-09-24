package com.ecommerce.backend.controller;
import com.ecommerce.backend.model.Order;
import com.ecommerce.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/saveOrUpdateOrder")
    public ResponseEntity<?> saveOrUpdateOrder(@RequestBody Order order){
        return new ResponseEntity<>(orderService.saveOrUpdateOrder(order), HttpStatus.OK);
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<?> getAllOrder(){
        return new ResponseEntity<>(orderService.getAllOrder(), HttpStatus.FOUND);
    }

    @PostMapping("/deleteOrder/{id}")
    public void deleteOrderById(@PathVariable Long id) throws Exception {
        orderService.deleteOrderById(id);
    }
}
