package com.ecommerce.backend.service.serviceImpl;

import com.ecommerce.backend.model.Payment;

public interface PaymentImpl {
    public Object saveOrUpdatePayment(Payment payment);
    public Object getAllPayment();
    public void deletePaymentById(Long id) throws Exception;
}
