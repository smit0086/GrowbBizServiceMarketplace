package com.growbiz.backend.Booking;

import com.growbiz.backend.Booking.helper.BookingControllerHelper;
import com.growbiz.backend.Booking.models.*;
import com.growbiz.backend.Business.model.Business;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.User.models.Role;
import com.growbiz.backend.User.models.User;
import org.apache.coyote.Response;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingControllerHelperTest {

    private static final String TEST_BOOKING_DATE = "2023-11-13";
    private static final LocalTime TEST_BOOKING_START_TIME = LocalTime.of(11, 30);
    private static final LocalTime TEST_BOOKING_END_TIME = LocalTime.of(12, 30);

    @InjectMocks
    private BookingControllerHelper bookingHelper;

    User mockUser;

    Business mockBusiness;

    SubCategory mockSubCategory;

    Services mockService;

    Booking mockBooking;

    BookingBusiness mockBookingBusiness;

    @BeforeEach
    public void init() {
        mockUser = User
                .builder()
                .id(1L)
                .email("test@dal.ca")
                .password("test")
                .firstName("John")
                .lastName("Doe")
                .role(Role.CUSTOMER)
                .build();

        mockBusiness = Business
                .builder()
                .businessId(1L)
                .businessName("Test Business")
                .build();

        mockSubCategory = SubCategory
                .builder()
                .subCategoryID(1L)
                .name("Test SubCategory")
                .build();

        mockService = Services
                .builder()
                .serviceId(1L)
                .serviceName("Test Service")
                .description("Test")
                .price(24.00)
                .timeRequired(LocalTime.of(0, 30))
                .business(mockBusiness)
                .subCategory(mockSubCategory)
                .build();

        mockBooking = Booking
                .builder()
                .id(1L)
                .user(mockUser)
                .service(mockService)
                .date(TEST_BOOKING_DATE)
                .startTime(TEST_BOOKING_START_TIME)
                .endTime(TEST_BOOKING_END_TIME)
                .amount(120.50)
                .note("Test")
                .status(BookingStatus.UPCOMING)
                .build();

        mockBookingBusiness = BookingBusiness
                .builder()
                .id(1L)
                .date(TEST_BOOKING_DATE)
                .startTime(TEST_BOOKING_START_TIME)
                .endTime(TEST_BOOKING_END_TIME)
                .amount(120.50)
                .note("Test")
                .status(BookingStatus.UPCOMING)
                .userEmail("test@dal.ca")
                .serviceName("Test Service")
                .timeRequired(LocalTime.of(0, 30))
                .build();
    }

    @Test
    public void createBookingResponseTest() {
        ResponseEntity<BookingResponse> actualResponse;
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        List<Booking> bookings = List.of(mockBooking);
        ResponseEntity<BookingResponse> expectedResponse = ResponseEntity.ok(
                BookingResponse.builder()
                        .bookings(List.of(mockBooking))
                        .subject(mockUser.getEmail())
                        .role(Role.CUSTOMER)
                        .build()
        );

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);
        actualResponse = bookingHelper.createBookingResponse(bookings);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void createBusinessBookingResponseTest() {
        ResponseEntity<BookingBusinessResponse> actualResponse;
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        List<BookingBusiness> bookings = List.of(mockBookingBusiness);
        ResponseEntity<BookingBusinessResponse> expectedResponse = ResponseEntity.ok(
                BookingBusinessResponse.builder()
                        .bookings(List.of(mockBookingBusiness))
                        .subject(mockUser.getEmail())
                        .role(Role.CUSTOMER)
                        .build()
        );

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);
        actualResponse = bookingHelper.createBookingBusinessResponse(bookings);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void convertBookingToBookingBusinessTest() {
        List<Booking> bookings = List.of(mockBooking);
        List<BookingBusiness> expectedBookingBusiness = List.of(mockBookingBusiness);
        List<BookingBusiness> actualBookingBusiness;

        actualBookingBusiness = bookingHelper.convertToBookingBusinessList(bookings);
        assertEquals(expectedBookingBusiness, actualBookingBusiness);
    }
}
