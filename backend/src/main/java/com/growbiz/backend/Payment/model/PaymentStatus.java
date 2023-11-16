package com.growbiz.backend.Payment.model;

public enum PaymentStatus {
    SUCCESS("payment_intent.succeeded"),
    CREATED("payment_intent.created"),
    CANCELED("payment_intent.canceled"),
    FAILED("payment_intent.payment_failed");

    PaymentStatus(String status) {
    }
}
