package com.ecommerce.backend.controller;
import com.ecommerce.backend.model.Payment;
import com.ecommerce.backend.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

        private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

        @Autowired
        private PaymentService paymentService;

        @PostMapping("/saveOrUpdatePayment")
        public ResponseEntity<?> saveOrUpdatePayment(@RequestBody Payment payment){
            logger.info("Saving or Updating Payment: {}", payment);
            try {
                return new ResponseEntity<>(paymentService.saveOrUpdatePayment(payment), HttpStatus.OK);
            } catch (Exception e) {
                logger.error("Error while saving/updating payment: {}", payment, e);
                return new ResponseEntity<>("Failed to save/update payment", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @GetMapping("/getAllPayments")
        public ResponseEntity<?> getAllPayment(){
            logger.debug("Fetching all payments");
            try {
                return new ResponseEntity<>(paymentService.getAllPayment(), HttpStatus.OK);
            } catch (Exception e) {
                logger.error("Error while fetching all payments", e);
                return new ResponseEntity<>("Failed to fetch payments", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PostMapping("/deletePayment/{id}")
        public ResponseEntity<?> deletePaymentById(@PathVariable Long id) {
            logger.warn("Deleting payment with ID: {}", id);
            try {
                paymentService.deletePaymentById(id);
                return new ResponseEntity<>("Payment deleted successfully", HttpStatus.OK);
            } catch (Exception e) {
                logger.error("Error while deleting payment with ID: {}", id, e);
                return new ResponseEntity<>("Failed to delete payment", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
}
