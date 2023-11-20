package com.growbiz.backend.Email;

import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Booking.models.BookingStatus;
import com.growbiz.backend.Booking.service.IBookingService;
import com.growbiz.backend.Business.model.Business;
import com.growbiz.backend.Business.service.IBusinessService;
import com.growbiz.backend.Email.controller.EmailController;
import com.growbiz.backend.Email.handler.EmailControllerHelper;
import com.growbiz.backend.Email.model.EmailResponse;
import com.growbiz.backend.Email.service.ISendEmailService;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.Services.service.IServicesService;
import com.growbiz.backend.TestConstants.TestConstants;
import com.growbiz.backend.User.models.Role;
import com.growbiz.backend.User.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmailControllerTest {

    @InjectMocks
    private EmailController emailController;

    @Mock
    private ISendEmailService sendEmailService;

    @Mock
    private IBookingService iBookingService;

    @Mock
    private IServicesService iServicesService;

    @Mock
    private IBusinessService iBusinessService;

    @Mock
    private EmailControllerHelper emailControllerHelper;
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
                .email("test@dal.ca")
                .password("test")
                .firstName("John")
                .lastName("Doe")
                .role(Role.CUSTOMER)
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
                .note("Test")
                .status(BookingStatus.UPCOMING)
                .build();
    }

    @Test
    public void sendEmailAndReturnOkResponse() {
        EmailResponse emailResponse = EmailResponse.builder()
                .businessName(mockBusiness.getBusinessName())
                .serviceName(mockService.getServiceName())
                .from("")
                .user(mockUser.getFirstName() + " " + mockBooking.getUser().getLastName())
                .time(mockBooking.getStartTime())
                .date(mockBooking.getDate()).build();

        when(iBookingService.findById(1L)).thenReturn(mockBooking);
        when(iServicesService.getServiceById(anyLong())).thenReturn(mockService);
        when(iBusinessService.findById(anyLong())).thenReturn(mockBusiness);

        when(emailControllerHelper.generateHeadSection(emailResponse)).thenReturn("Opening Complete");
        when(emailControllerHelper.generateMessageBody(emailResponse)).thenReturn("Message Body Complete");
        when(emailControllerHelper.generateEndSection(emailResponse)).thenReturn("Conclusion Complete");

        ResponseEntity<String> response = emailController.sendEmailReminder(1L);

        assertEquals("Email has been sent to the given email " + mockBooking.getUser().getEmail(), response.getBody());
        assertEquals(200, response.getStatusCodeValue()); // Assuming OK status code
        verify(sendEmailService).sendEmail(eq(mockBooking.getUser().getEmail()), anyString(), anyString());
    }

}
