package com.growbiz.backend.Payment;

import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Booking.service.IBookingService;
import com.growbiz.backend.Business.models.Business;
import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Enums.BookingStatus;
import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.Payment.helper.PaymentServiceHelper;
import com.growbiz.backend.Payment.model.Payment;
import com.growbiz.backend.Payment.repository.IPaymentRepository;
import com.growbiz.backend.RequestResponse.Payment.PaymentRequest;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.Services.service.IServicesService;
import com.growbiz.backend.TestConstants.TestConstants;
import com.growbiz.backend.User.models.User;
import com.growbiz.backend.User.service.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceHelperTest {

    @InjectMocks
    private PaymentServiceHelper paymentServiceHelperMock;
    @Mock
    IPaymentRepository paymentRepositoryMock;
    @Mock
    IServicesService servicesServiceMock;
    @Mock
    IBookingService bookingServiceMock;
    @Mock
    IUserService userServiceMock;
    @Mock
    Services mockService;
    @Mock
    Booking mockedBooking;
    Long expectedPaymentAmount = 2692L;

    @Test
    void testCalculatePaymentAmount() {
        Business mockBusiness = mock(Business.class);
        Category mockCategory = Category
                .builder()
                .categoryID(TestConstants.TEST_ID_1)
                .name(TestConstants.TEST_CATEGORY_NAME)
                .tax(TestConstants.TEST_CATEGORY_TAX)
                .build();
        SubCategory mockSubCategory = SubCategory
                .builder()
                .subCategoryID(TestConstants.TEST_ID_1)
                .name(TestConstants.TEST_SUBCATEGORY_NAME)
                .category(mockCategory)
                .build();
        mockService = Services
                .builder()
                .serviceId(TestConstants.TEST_ID_1)
                .serviceName(TestConstants.TEST_SERVICE_NAME)
                .description(TestConstants.TEST_SERVICE_DESCRIPTION)
                .price(TestConstants.TEST_SERVICE_PRICE)
                .imageURL(TestConstants.TEST_SERVICE_IMAGE_URL)
                .business(mockBusiness)
                .subCategory(mockSubCategory)
                .build();
        when(servicesServiceMock.getServiceById(anyLong())).thenReturn(mockService);
        Long actualPaymentAmount = paymentServiceHelperMock.calculatePaymentAmount(1L);
        Assertions.assertEquals(expectedPaymentAmount, actualPaymentAmount);
    }

    @Test
    void testSaveToBooking() {
        Business mockBusiness = mock(Business.class);
        User mockUser = mock(User.class);
        Booking mockBooking = Booking.builder()
                .id(1L)
                .service(mockService)
                .note(TestConstants.TEST_NOTE)
                .date(TestConstants.TEST_DATE)
                .status(BookingStatus.UPCOMING)
                .user(mockUser)
                .startTime(TestConstants.TEST_START_LOCAL_TIME)
                .endTime(TestConstants.TEST_END_LOCAL_TIME)
                .amount((double) TestConstants.TEST_AMOUNT)
                .build();
        Payment mockedPayment = Payment.builder()
                .serviceId(TestConstants.TEST_ID_1)
                .date(TestConstants.TEST_DATE)
                .userEmail(TestConstants.TEST_EMAIL)
                .startTime(TestConstants.TEST_START_LOCAL_TIME)
                .endTime(TestConstants.TEST_START_LOCAL_TIME)
                .note(TestConstants.TEST_NOTE)
                .build();
        when(userServiceMock.getUserByEmailAndRole(anyString(), anyString())).thenReturn(mockUser);
        when(servicesServiceMock.getServiceById(anyLong())).thenReturn(mockService);
        when(bookingServiceMock.save(any(Booking.class))).thenReturn(mockBooking);
        Booking expectedBooking = paymentServiceHelperMock.saveToBooking(mockedPayment, 1000L);
        Assertions.assertEquals(expectedBooking, mockBooking);
    }

    @Test
    void testCreatePayment() {
        User mockedUser = User.builder()
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
        PaymentRequest mockedPaymentRequest = PaymentRequest.builder()
                .email(TestConstants.TEST_EMAIL)
                .role(Role.CUSTOMER)
                .serviceId(1L)
                .date(TestConstants.TEST_DATE)
                .note(TestConstants.TEST_NOTE)
                .startTime(TestConstants.TEST_START_LOCAL_TIME)
                .endTime(TestConstants.TEST_BOOKING_END_TIME)
                .build();
        Payment expectedPayment = Payment.builder()
                .serviceId(TestConstants.TEST_ID_1)
                .date(TestConstants.TEST_DATE)
                .userEmail(TestConstants.TEST_EMAIL)
                .startTime(TestConstants.TEST_START_LOCAL_TIME)
                .endTime(TestConstants.TEST_BOOKING_END_TIME)
                .note(TestConstants.TEST_NOTE)
                .amount((double) (TestConstants.TEST_AMOUNT / 100L))
                .build();
        Assertions.assertEquals(expectedPayment, paymentServiceHelperMock.createPayment(mockedPaymentRequest, TestConstants.TEST_AMOUNT));
    }
}
