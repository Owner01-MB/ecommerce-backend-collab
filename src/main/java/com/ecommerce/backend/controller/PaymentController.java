package com.ecommerce.backend.controller;
import com.ecommerce.backend.dto.PaymentDto;
import com.ecommerce.backend.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

        private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

        @PostMapping("/saveOrUpdatePayment")
        public ResponseEntity<?> saveOrUpdatePayment(@RequestBody PaymentDto paymentDto) {
            logger.info("Received request to saveOrUpdate payment: {}", paymentDto);
            try {
                Object response = paymentService.saveOrUpdatePayment(paymentDto);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                logger.error("Error while saving/updating payment: {}", paymentDto, e);
                return new ResponseEntity<>("Error while saving/updating payment: " + e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @GetMapping("/getAllPayments")
        public ResponseEntity<?> getAllPayments() {
            logger.info("Received request to fetch all payments");
            try {
                List<PaymentDto> payments = paymentService.getAllPayments();
                return new ResponseEntity<>(payments, HttpStatus.OK);
            } catch (Exception e) {
                logger.error("Error while fetching payments", e);
                return new ResponseEntity<>("Error while fetching payments: " + e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @DeleteMapping("/deletePayment/{id}")
        public ResponseEntity<?> deletePaymentById(@PathVariable Long id) {
            logger.info("Received request to delete payment with ID: {}", id);
            try {
                paymentService.deletePaymentById(id);
                return new ResponseEntity<>("Deleted Successfully!!!", HttpStatus.OK);
            } catch (Exception e) {
                logger.error("Error while deleting payment with ID: {}", id, e);
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        }
}
