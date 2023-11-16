package com.growbiz.backend.Payment.model;

public enum PaymentStatus {
    SUCCESS("payment_intent.succeeded"),
    CREATED("payment_intent.created");

    PaymentStatus(String status) {
    }
}
