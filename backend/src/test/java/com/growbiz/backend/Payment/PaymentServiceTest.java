package com.growbiz.backend.Payment;

import com.growbiz.backend.Payment.model.Payment;
import com.growbiz.backend.Payment.model.PaymentRequest;
import com.growbiz.backend.Payment.repository.IPaymentRepository;
import com.growbiz.backend.Payment.service.PaymentService;
import com.growbiz.backend.TestConstants.TestConstants;
import com.growbiz.backend.User.models.Role;
import com.growbiz.backend.User.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
    @InjectMocks
    private PaymentService paymentServiceMock;
    @Mock
    private IPaymentRepository paymentRepositoryMock;
    @Mock
    private PaymentRequest mockedPaymentRequest;
    @Mock
    private User mockedUser;
    @Mock
    Payment mockedPayment;

    @BeforeEach
    public void init() {
        mockedPayment = Payment.builder()
                .serviceId(1L)
                .date(TestConstants.TEST_DATE)
                .userEmail(TestConstants.TEST_EMAIL)
                .startTime(TestConstants.TEST_LOCAL_TIME)
                .endTime(TestConstants.TEST_LOCAL_TIME)
                .note(TestConstants.TEST_NOTE)
                .amount(10.0)
                .build();
    }

    @Test
    public void testAddPayment() {
        mockedUser = User.builder()
                .id(1L)
                .email(TestConstants.TEST_EMAIL)
                .password(TestConstants.TEST_PASSWORD)
                .firstName(TestConstants.TEST_NAME)
                .lastName(TestConstants.TEST_NAME)
                .build();
        Authentication mockedAuthentication = mock(Authentication.class);
        SecurityContext mockedSecurityContext = mock(SecurityContext.class);
        when(mockedSecurityContext.getAuthentication()).thenReturn(mockedAuthentication);
        SecurityContextHolder.setContext(mockedSecurityContext);
        when(mockedAuthentication.getPrincipal()).thenReturn(mockedUser);
        when(paymentRepositoryMock.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        mockedPaymentRequest = PaymentRequest.builder()
                .email(TestConstants.TEST_EMAIL)
                .role(Role.CUSTOMER)
                .serviceId(1L)
                .date(TestConstants.TEST_DATE)
                .note(TestConstants.TEST_NOTE)
                .startTime(TestConstants.TEST_LOCAL_TIME)
                .endTime(TestConstants.TEST_LOCAL_TIME)
                .build();
        Payment actualPayment = paymentServiceMock.addPayment(mockedPaymentRequest, TestConstants.TEST_AMOUNT);
        Assertions.assertEquals(mockedPayment, actualPayment);
    }

    @Test
    public void testCreatePaymentIntent() {
        //paymentService.createPaymentIntent();
    }

    @Test
    public void testHandleWebhook() {
        //paymentService.handleWebhook();
    }

    @Test
    public void testFindPaymentById() {
        //paymentService.findPaymentById(1L);
    }
}
