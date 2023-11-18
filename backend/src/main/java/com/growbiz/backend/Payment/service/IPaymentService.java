package com.growbiz.backend.Payment.service;

import com.growbiz.backend.Payment.model.Payment;
import com.growbiz.backend.Payment.model.PaymentRequest;
import com.growbiz.backend.Payment.model.PaymentResponse;
import com.growbiz.backend.Payment.model.PaymentStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPaymentService {
    Payment addPayment(PaymentRequest paymentRequest, long amount);

    Payment findPaymentById(Long paymentId);

    List<Payment> findAllPayments();

    List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);

    ResponseEntity<String> handleWebhook(String requestBody, String sigHeader);

    ResponseEntity<PaymentResponse> createPaymentIntent(PaymentRequest paymentRequest);

    List<Payment> findByServiceId(Long serviceId);
}
