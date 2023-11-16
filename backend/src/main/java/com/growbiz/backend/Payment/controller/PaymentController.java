package com.growbiz.backend.Payment.controller;

import com.growbiz.backend.Payment.model.Payment;
import com.growbiz.backend.Payment.model.PaymentRequest;
import com.growbiz.backend.Payment.model.PaymentResponse;
import com.growbiz.backend.Payment.service.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    IPaymentService paymentService;

    @PostMapping(path = "/init")
    public ResponseEntity<PaymentResponse> createPaymentIntent(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.createPaymentIntent(paymentRequest);
    }

    @PostMapping(path = "/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String requestBody, @RequestHeader("Stripe-Signature") String sigHeader) {
        return paymentService.handleWebhook(requestBody, sigHeader);
    }

    @GetMapping
    public ResponseEntity<Payment> getPayment(@RequestParam Long paymentId) {
        return ResponseEntity.ok(paymentService.findPaymentById(paymentId));
    }

    @GetMapping(path = "/status")
    public ResponseEntity<List<Payment>> getAllPayments(@RequestParam String paymentStatus) {
        return ResponseEntity.ok(paymentService.findAllPayments());
    }
}
