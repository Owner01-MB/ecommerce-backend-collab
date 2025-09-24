package com.ecommerce.backend.service;

import com.ecommerce.backend.model.Payment;
import com.ecommerce.backend.repository.PaymentRepo;
import com.ecommerce.backend.service.serviceImpl.PaymentImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService implements PaymentImpl {
    @Autowired
    private PaymentRepo paymentRepo;

    @Override
    public Object saveOrUpdatePayment(Payment payment) {
        if (payment.getPaymentId() != null && paymentRepo.existsById(payment.getPaymentId())) {
            Payment existingPayment = paymentRepo.findById(payment.getPaymentId()).get();

            existingPayment.setPaymentMethod(payment.getPaymentMethod());

            paymentRepo.save(existingPayment);
            return "Updated Successfully!!!";
        } else {
            paymentRepo.save(payment);
            return "Inserted Successfully!!!";
        }
    }

    @Override
    public Object getAllPayment() {
        return paymentRepo.findAll();
    }

    @Override
    public void deletePaymentById(Long id) throws Exception {
        Optional<Payment> optional = paymentRepo.findById(id);
        if (optional.isPresent()) {
            paymentRepo.deleteById(id);
        } else {
            throw new Exception("Id not Found!!!");
        }
    }
}
