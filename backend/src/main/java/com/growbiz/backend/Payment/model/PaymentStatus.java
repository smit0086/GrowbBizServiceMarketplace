package com.growbiz.backend.Payment.model;

import java.util.Arrays;
import java.util.Optional;

public enum PaymentStatus {
    SUCCESS("payment_intent.succeeded"),
    INITIATED("payment_intent.initiated");
    private final String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    public static String valueOfStatus(String status) {
        Optional<PaymentStatus> paymentStatusOptional = Arrays.stream(values()).filter(paymentStatus -> status.equals(paymentStatus.status)).findFirst();
        return paymentStatusOptional.map(paymentStatus -> paymentStatus.status).orElse(null);
    }
}
