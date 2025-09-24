package com.ecommerce.backend.controller;
import com.ecommerce.backend.model.Payment;
import com.ecommerce.backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {


    @Autowired
    private PaymentService paymentService;

    @PostMapping("/saveOrUpdatePayment")
    public ResponseEntity<?> saveOrUpdatePayment(@RequestBody Payment payment){
        return new ResponseEntity<>(paymentService.saveOrUpdatePayment(payment), HttpStatus.OK);
    }

    @GetMapping("/getAllPayments")
    public ResponseEntity<?> getAllPayment(){
        return new ResponseEntity<>(paymentService.getAllPayment(), HttpStatus.FOUND);
    }

    @PostMapping("/deletePayment/{id}")
    public void deletePaymentById(@PathVariable Long id) throws Exception {
        paymentService.deletePaymentById(id);
    }
}
