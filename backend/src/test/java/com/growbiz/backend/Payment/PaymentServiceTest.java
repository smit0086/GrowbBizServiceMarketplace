package com.growbiz.backend.Payment;

import com.growbiz.backend.Payment.model.Payment;
import com.growbiz.backend.Payment.model.PaymentStatus;
import com.growbiz.backend.Payment.repository.IPaymentRepository;
import com.growbiz.backend.Payment.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
    @InjectMocks
    private PaymentService paymentService;
    @Mock
    private IPaymentRepository paymentRepository;
    Payment payment;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        payment = Payment.builder()
                .serviceId(1L)
                .date("testDate")
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(9, 30))
                .note("testNote")
                .paymentStatus(PaymentStatus.INITIATED)
                .build();
    }

    @Test
    public void testAddPayment() {
        paymentService.addPayment(payment);
    }

    @Test
    public void testCreatePaymentIntent() {
        paymentService.createPaymentIntent();
    }

    @Test
    public void testHandleWebhook() {
        paymentService.handleWebhook();
    }

    @Test
    public void testFindPaymentById() {
        paymentService.findPaymentById(1L);
    }
}
