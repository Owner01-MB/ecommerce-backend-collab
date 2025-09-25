package com.ecommerce.backend.service.serviceImpl;

import com.ecommerce.backend.dto.PaymentDto;
import com.ecommerce.backend.model.Payment;

import java.util.List;

public interface PaymentImpl {
    Object saveOrUpdatePayment(PaymentDto paymentDto);
    List<PaymentDto> getAllPayments();
    void deletePaymentById(Long id) throws Exception;
}
