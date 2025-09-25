package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.PaymentDto;
import com.ecommerce.backend.model.Payment;
import com.ecommerce.backend.repository.PaymentRepo;
import com.ecommerce.backend.service.serviceImpl.PaymentImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService implements PaymentImpl {

    @Autowired
    private PaymentRepo paymentRepo;

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    @Override
    public Object saveOrUpdatePayment(PaymentDto paymentDto) {
        if (paymentDto.getPaymentId() != null && paymentRepo.existsById(paymentDto.getPaymentId())) {
            Payment existingPayment = paymentRepo.findById(paymentDto.getPaymentId()).get();
            existingPayment.setPaymentMethod(paymentDto.getPaymentMethod());
            paymentRepo.save(existingPayment);
            logger.info("Payment updated successfully with ID: {}", paymentDto.getPaymentId());
            return "Updated Successfully!!!";
        } else {
            Payment newPayment = new Payment();
            newPayment.setPaymentMethod(paymentDto.getPaymentMethod());
            paymentRepo.save(newPayment);
            logger.info("New Payment inserted with ID: {}", newPayment.getPaymentId());
            return "Inserted Successfully!!!";
        }
    }

    @Override
    public List<PaymentDto> getAllPayments() {
        List<Payment> payments = paymentRepo.findAll();
        logger.info("Fetched all payments, count: {}", payments.size());

        List<PaymentDto> paymentDtos = new ArrayList<>();
        for (Payment payment : payments) {
            PaymentDto dto = new PaymentDto();
            dto.setPaymentId(payment.getPaymentId());
            dto.setPaymentMethod(payment.getPaymentMethod());
            paymentDtos.add(dto);
        }
        return paymentDtos;
    }

    @Override
    public void deletePaymentById(Long id) throws Exception {
        logger.info("Delete requested for payment ID: {}", id);
        Optional<Payment> optional = paymentRepo.findById(id);
        if (optional.isPresent()) {
            paymentRepo.deleteById(id);
            logger.info("Payment with ID {} has been deleted successfully.", id);
        } else {
            logger.error("Payment not found with ID: {}", id);
            throw new Exception("Id not Found!!!");
        }
    }

}
