package com.growbiz.backend.Payment;

import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.Payment.helper.PaymentServiceHelper;
import com.growbiz.backend.Payment.model.Payment;
import com.growbiz.backend.Payment.repository.IPaymentRepository;
import com.growbiz.backend.Payment.service.PaymentService;
import com.growbiz.backend.RequestResponse.Payment.PaymentRequest;
import com.growbiz.backend.TestConstants.TestConstants;
import org.assertj.core.util.IterableUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
    private static final double AMOUNT = 10.0;
    @InjectMocks
    private PaymentService paymentServiceMock;
    @Mock
    private PaymentRequest mockedPaymentRequest;
    @Mock
    private IPaymentRepository paymentRepositoryMock;
    @Mock
    Payment mockedPayment;
    @Mock
    PaymentServiceHelper helperMock;

    @BeforeEach
    public void init() {
        mockedPayment = Payment.builder()
                .serviceId(TestConstants.TEST_ID_1)
                .date(TestConstants.TEST_DATE)
                .userEmail(TestConstants.TEST_EMAIL)
                .startTime(TestConstants.TEST_START_LOCAL_TIME)
                .endTime(TestConstants.TEST_START_LOCAL_TIME)
                .note(TestConstants.TEST_NOTE)
                .amount(AMOUNT)
                .build();
    }

    @Test
    void testAddPayment() {
        mockedPaymentRequest = PaymentRequest.builder()
                .email(TestConstants.TEST_EMAIL)
                .role(Role.CUSTOMER)
                .serviceId(TestConstants.TEST_ID_1)
                .date(TestConstants.TEST_DATE)
                .note(TestConstants.TEST_NOTE)
                .startTime(TestConstants.TEST_START_LOCAL_TIME)
                .endTime(TestConstants.TEST_START_LOCAL_TIME)
                .build();
        when(helperMock.createPayment(Mockito.eq(mockedPaymentRequest), anyLong())).thenReturn(mockedPayment);
        when(paymentRepositoryMock.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Payment actualPayment = paymentServiceMock.addPayment(mockedPaymentRequest, TestConstants.TEST_AMOUNT);
        Assertions.assertEquals(mockedPayment, actualPayment);
    }

    @Test
    void testFindPaymentById() {
        when(paymentRepositoryMock.findById(anyLong())).thenReturn(Optional.of(mockedPayment));
        Payment actualResponse = paymentServiceMock.findPaymentById(1L);
        Assertions.assertEquals(mockedPayment, actualResponse);
    }

    @Test
    void testFindAllPayments() {
        when(paymentRepositoryMock.findAll()).thenReturn(IterableUtil.iterable(mockedPayment));
        List<Payment> actualResponse = paymentServiceMock.findAllPayments();
        Assertions.assertEquals(List.of(mockedPayment), actualResponse);
    }

    @Test
    void testFindAllPaymentsByUserEmail() {
        when(paymentRepositoryMock.findByUserEmail(TestConstants.TEST_EMAIL)).thenReturn(List.of(mockedPayment));
        List<Payment> actualResponse = paymentServiceMock.findAllPaymentsByUserEmail(TestConstants.TEST_EMAIL);
        Assertions.assertEquals(List.of(mockedPayment), actualResponse);
    }

    @Test
    void testFindByServiceId() {
        when(paymentRepositoryMock.findByServiceId(1L)).thenReturn(List.of(mockedPayment));
        List<Payment> actualResponse = paymentServiceMock.findByServiceId(1L);
        Assertions.assertEquals(List.of(mockedPayment), actualResponse);
    }
}
