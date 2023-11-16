package com.growbiz.backend.Payment.service;

import com.growbiz.backend.Payment.model.Payment;
import com.growbiz.backend.Payment.model.PaymentRequest;
import com.growbiz.backend.Payment.model.PaymentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IPaymentService {
    Payment addPayment(PaymentRequest paymentRequest);

    Payment findPaymentById(Long paymentId);

    ResponseEntity<String> handleWebhook(String requestBody);

    ResponseEntity<PaymentResponse> createPaymentIntent(PaymentRequest paymentRequest);
}
