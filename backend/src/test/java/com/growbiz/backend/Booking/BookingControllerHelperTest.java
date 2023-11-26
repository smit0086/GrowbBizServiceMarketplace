package com.growbiz.backend.Booking;

import com.growbiz.backend.Booking.helper.BookingControllerHelper;
import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Booking.models.BookingBusiness;
import com.growbiz.backend.Business.models.Business;
import com.growbiz.backend.BusinessHour.model.BusinessHour;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Enums.BookingStatus;
import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.FreeSlot.models.SlotRange;
import com.growbiz.backend.RequestResponse.Booking.BookingBusinessResponse;
import com.growbiz.backend.RequestResponse.Booking.BookingResponse;
import com.growbiz.backend.RequestResponse.BusinessHour.BusinessHourResponse;
import com.growbiz.backend.RequestResponse.FreeSlot.FreeSlotsResponse;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.TestConstants.TestConstants;
import com.growbiz.backend.User.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingControllerHelperTest {

    private static final String TEST_BOOKING_DATE = "2023-11-13";
    private static final int TEST_SERVICE_TIME_REQ_MIN = 30;
    private static final LocalTime TEST_BOOKING_START_TIME = LocalTime.of(11, TEST_SERVICE_TIME_REQ_MIN);
    private static final LocalTime TEST_BOOKING_END_TIME = LocalTime.of(12, TEST_SERVICE_TIME_REQ_MIN);
    private static final long TEST_ID = 1L;
    private static final double TEST_SERVICE_PRICE = 24.00;
    private static final double TEST_BOOKING_AMOUNT = 120.50;
    private static final int TEST_SERVICE_TIME_REQ_HR = 0;

    private static final LocalTime TEST_BH_START_TIME = LocalTime.of(9,0);
    private static final LocalTime TEST_BH_END_TIME = LocalTime.of(5,0);

    private static final LocalTime TEST_FS_START_TIME = LocalTime.of(11, 0);
    private static final LocalTime TEST_FS_END_TIME = LocalTime.of(12, 0);

    @InjectMocks
    private BookingControllerHelper bookingHelper;

    User mockUser;

    Business mockBusiness;

    SubCategory mockSubCategory;

    Services mockService;

    Booking mockBooking;

    BookingBusiness mockBookingBusiness;

    BusinessHour mockBusinessHour;

    Map<Date, List<SlotRange>> mockFreeSlots;

    @BeforeEach
    public void init() {
        mockUser = User
                .builder()
                .id(TEST_ID)
                .email(TestConstants.TEST_EMAIL)
                .password(TestConstants.TEST_PASSWORD)
                .firstName("John")
                .lastName("Doe")
                .role(Role.CUSTOMER)
                .build();

        mockBusiness = Business
                .builder()
                .businessId(TEST_ID)
                .businessName(TestConstants.TEST_BUSINESS_NAME)
                .build();

        mockSubCategory = SubCategory
                .builder()
                .subCategoryID(TEST_ID)
                .name(TestConstants.TEST_SUBCATEGORY_NAME)
                .build();

        mockService = Services
                .builder()
                .serviceId(TEST_ID)
                .serviceName(TestConstants.TEST_SERVICE_NAME)
                .description(TestConstants.TEST_SERVICE_DESCRIPTION)
                .price(TEST_SERVICE_PRICE)
                .timeRequired(LocalTime.of(TEST_SERVICE_TIME_REQ_HR, TEST_SERVICE_TIME_REQ_MIN))
                .business(mockBusiness)
                .subCategory(mockSubCategory)
                .build();

        mockBooking = Booking
                .builder()
                .id(TEST_ID)
                .user(mockUser)
                .service(mockService)
                .date(TEST_BOOKING_DATE)
                .startTime(TEST_BOOKING_START_TIME)
                .endTime(TEST_BOOKING_END_TIME)
                .amount(TEST_BOOKING_AMOUNT)
                .note("Test")
                .status(BookingStatus.UPCOMING)
                .build();

        mockBookingBusiness = BookingBusiness
                .builder()
                .id(TEST_ID)
                .date(TEST_BOOKING_DATE)
                .startTime(TEST_BOOKING_START_TIME)
                .endTime(TEST_BOOKING_END_TIME)
                .amount(TEST_BOOKING_AMOUNT)
                .note("Test")
                .status(BookingStatus.UPCOMING)
                .userEmail(TestConstants.TEST_EMAIL)
                .serviceName(TestConstants.TEST_SERVICE_NAME)
                .timeRequired(LocalTime.of(TEST_SERVICE_TIME_REQ_HR, TEST_SERVICE_TIME_REQ_MIN))
                .build();

        mockBusinessHour = BusinessHour.builder()
                .monday_start(TEST_BH_START_TIME)
                .monday_end(TEST_BH_END_TIME)
                .tuesday_start(TEST_BH_START_TIME)
                .tuesday_end(TEST_BH_END_TIME)
                .wednesday_start(TEST_BH_START_TIME)
                .wednesday_end(TEST_BH_END_TIME)
                .thursday_start(TEST_BH_START_TIME)
                .thursday_end(TEST_BH_END_TIME)
                .friday_start(TEST_BH_START_TIME)
                .friday_end(TEST_BH_END_TIME)
                .saturday_start(TEST_BH_START_TIME)
                .saturday_end(TEST_BH_END_TIME)
                .sunday_start(TEST_BH_START_TIME)
                .sunday_end(TEST_BH_END_TIME)
                .build();

        mockFreeSlots = new HashMap<>();
        mockFreeSlots.put(
                new GregorianCalendar(TestConstants.TEST_YEAR, Calendar.NOVEMBER, TestConstants.TEST_DAY).getTime(),
                List.of(SlotRange.builder().startTime(TEST_FS_START_TIME).endTime(TEST_FS_END_TIME).build()));
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

    @Test
    public void createBusinessHourResponseTest() {
        ResponseEntity<BusinessHourResponse> actualResponse;
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        ResponseEntity<BusinessHourResponse> expectedResponse = ResponseEntity.ok(
                BusinessHourResponse.builder()
                        .businessHour(mockBusinessHour)
                        .subject(mockUser.getEmail())
                        .role(mockUser.getRole())
                        .build()
        );

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);
        actualResponse = bookingHelper.createBusinessHourResponse(mockBusinessHour);
        assertEquals(expectedResponse, actualResponse);

    }

    @Test
    public void createFreeSlotsResponse() {
        ResponseEntity<FreeSlotsResponse> actualResponse;
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        ResponseEntity<FreeSlotsResponse> expectedResponse = ResponseEntity.ok(
                FreeSlotsResponse.builder()
                        .freeSlots(mockFreeSlots)
                        .subject(mockUser.getEmail())
                        .role(mockUser.getRole())
                        .build()
        );

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);
        actualResponse = bookingHelper.createFreeSlotsResponse(mockFreeSlots);
        assertEquals(expectedResponse, actualResponse);
    }
}
