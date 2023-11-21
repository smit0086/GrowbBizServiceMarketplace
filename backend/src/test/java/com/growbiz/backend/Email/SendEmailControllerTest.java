package com.growbiz.backend.Email;

import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Booking.service.IBookingService;
import com.growbiz.backend.Business.models.Business;
import com.growbiz.backend.Email.controller.EmailController;
import com.growbiz.backend.Email.model.EmailRequest;
import com.growbiz.backend.Email.service.ISendEmailService;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.TestConstants.TestConstants;
import com.growbiz.backend.User.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;


import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SendEmailControllerTest {

    @InjectMocks
    private EmailController emailController;

    @Mock
    private ISendEmailService sendEmailService;

    @Mock
    private IBookingService iBookingService;

    Business mockBusiness;
    Services mockService;
    Booking mockBooking;
    User mockUser;
    @BeforeEach
    public void init () {
        MockitoAnnotations.openMocks(this);
        mockBusiness = Business
                .builder()
                .businessId(1L)
                .businessName(TestConstants.TEST_BUSINESS_NAME)
                .email(TestConstants.TEST_EMAIL)
                .build();
        mockService = Services
                .builder()
                .serviceId(1L)
                .serviceName(TestConstants.TEST_SERVICE_NAME)
                .description(TestConstants.TEST_SERVICE_DESCRIPTION)
                .price(TestConstants.TEST_SERVICE_PRICE)
                .imageURL(TestConstants.TEST_SERVICE_IMAGE_URL)
                .business(mockBusiness)
                .build();
        mockUser = User
                .builder()
                .id(1L)
                .email(TestConstants.TEST_EMAIL)
                .password(TestConstants.TEST_PASSWORD)
                .firstName("John")
                .lastName("Doe")
                .build();
        mockBooking = Booking
                .builder()
                .id(1L)
                .user(mockUser)
                .service(mockService)
                .date(TestConstants.TEST_BOOKING_DATE)
                .startTime(TestConstants.TEST_BOOKING_START_TIME)
                .endTime(TestConstants.TEST_BOOKING_END_TIME)
                .amount(TestConstants.TEST_SERVICE_PRICE)
                .note(TestConstants.TEST_NOTE)
                .build();
    }

    @Test
    public void sendEmailAndReturnOkResponse() {
        EmailRequest emailResponse = EmailRequest.builder()
                .businessName(mockBusiness.getBusinessName())
                .serviceName(mockService.getServiceName())
                .user(mockUser.getFirstName() + " " + mockBooking.getUser().getLastName())
                .to(TestConstants.TEST_EMAIL)
                .subject("Subject: Confirmation of Your Upcoming Service Appointment")
                .time(mockBooking.getStartTime())
                .date(mockBooking.getDate()).build();

        when(iBookingService.findById(1L)).thenReturn(mockBooking);

        ResponseEntity<String> response = emailController.sendEmailReminder(1L);
        Locale locale = Locale.getDefault();
        assertEquals(200, response.getStatusCodeValue());
        verify(sendEmailService).sendEmailWithHtmlTemplate(emailResponse,"emailTemplate",locale);
    }

}
