package com.growbiz.backend.Booking;

import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Booking.repository.IBookingRepository;
import com.growbiz.backend.Booking.service.BookingService;
import com.growbiz.backend.Business.models.Business;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Enums.BookingStatus;
import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.Exception.exceptions.Booking.BookingNotFoundException;
import com.growbiz.backend.RequestResponse.Booking.BookingRequest;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.Services.service.IServicesService;
import com.growbiz.backend.User.models.User;
import com.growbiz.backend.User.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    private static final String TEST_BOOKING_DATE = "2023-11-13";
    public static final int TEST_SERVICE_TIME_REQ_MIN = 30;
    private static final LocalTime TEST_BOOKING_START_TIME = LocalTime.of(11, TEST_SERVICE_TIME_REQ_MIN);
    private static final LocalTime TEST_BOOKING_END_TIME = LocalTime.of(12, TEST_SERVICE_TIME_REQ_MIN);
    public static final double TEST_BOOKING_AMOUNT = 120.50;
    public static final long TEST_ID = 1L;
    public static final double TEST_SERVICE_PRICE = 24.00;
    public static final int TEST_SERVICE_TIME_REQ_HR = 0;

    @InjectMocks
    BookingService bookingServiceMock;

    @Mock
    IBookingRepository bookingRepository;

    @Mock
    IUserService userService;

    @Mock
    IServicesService servicesService;

    @Mock
    User mockUser;

    @Mock
    Business mockBusiness;

    @Mock
    SubCategory mockSubCategory;

    Services mockService;

    Booking mockBooking;

    BookingRequest mockBookingRequest;

    @BeforeEach
    public void init() {
        mockBookingRequest = BookingRequest
                .builder()
                .serviceId(TEST_ID)
                .date(TEST_BOOKING_DATE)
                .startTime(TEST_BOOKING_START_TIME)
                .endTime(TEST_BOOKING_END_TIME)
                .amount(TEST_BOOKING_AMOUNT)
                .note("Test")
                .status(BookingStatus.UPCOMING)
                .email("test@dal.ca")
                .role(Role.CUSTOMER)
                .build();

        mockUser = User
                .builder()
                .id(TEST_ID)
                .email("test@dal.ca")
                .password("test")
                .firstName("John")
                .lastName("Doe")
                .role(Role.CUSTOMER)
                .build();

        mockBusiness = Business
                .builder()
                .businessId(TEST_ID)
                .businessName("Test Business")
                .build();

        mockSubCategory = SubCategory
                .builder()
                .subCategoryID(TEST_ID)
                .name("Test SubCategory")
                .build();

        mockService = Services
                .builder()
                .serviceId(TEST_ID)
                .serviceName("Test Service")
                .description("Test")
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
    }

    @Test
    public void getBookingByBookingIdSuccessTest() {
        when(bookingRepository.findById(TEST_ID)).thenReturn(Optional.of(mockBooking));
        Booking actualBooking = bookingServiceMock.findById(TEST_ID);
        assertEquals(mockBooking, actualBooking);
    }

    @Test
    public void getBookingByBookingIdFailureTest() {
        assertThrows(BookingNotFoundException.class, () -> bookingServiceMock.findById(2L));
    }

    @Test
    public void getBookingByServiceIdTest() {
        when(bookingRepository.findByServiceServiceId(TEST_ID)).thenReturn(List.of(mockBooking));
        List<Booking> actualBooking = bookingServiceMock.findByServiceId(TEST_ID);
        assertEquals(List.of(mockBooking), actualBooking);
    }

    @Test
    public void getBookingByUserIdTest() {
        when(bookingRepository.findByUserId(TEST_ID)).thenReturn(List.of(mockBooking));
        List<Booking> actualBooking = bookingServiceMock.findByUserId(TEST_ID);
        assertEquals(List.of(mockBooking), actualBooking);
    }

    @Test
    public void getBookingByUserIdAndStatusTest() {
        when(bookingRepository.findByUserIdAndStatus(TEST_ID, BookingStatus.UPCOMING)).thenReturn(List.of(mockBooking));
        List<Booking> actualBooking = bookingServiceMock.getAllBookingsByUserIdAndStatus(TEST_ID, "UPCOMING");
        assertEquals(List.of(mockBooking), actualBooking);
    }

    @Test
    public void getBookingByBusinessIdTest() {
        when(bookingRepository.findByServiceBusinessBusinessId(TEST_ID)).thenReturn(List.of(mockBooking));
        List<Booking> actualBooking = bookingServiceMock.findByBusinessId(TEST_ID);
        assertEquals(List.of(mockBooking), actualBooking);
    }

    @Test
    public void getBookingByBusinessIdAndStatusTest() {
        when(bookingRepository.findByServiceBusinessBusinessIdAndStatus(TEST_ID, BookingStatus.UPCOMING)).thenReturn(List.of(mockBooking));
        List<Booking> actualBooking = bookingServiceMock.getAllBookingsByBusinessIdAndStatus(TEST_ID, BookingStatus.UPCOMING);
        assertEquals(List.of(mockBooking), actualBooking);
    }

    @Test
    public void saveBookingTest() {
        bookingServiceMock.save(mockBooking);
    }

    @Test
    public void saveBookingRequestTest() {
        Booking actualBooking;
        Booking expectedBooking = Booking.builder()
                .user(mockUser)
                .service(mockService)
                .date(TEST_BOOKING_DATE)
                .startTime(TEST_BOOKING_START_TIME)
                .endTime(TEST_BOOKING_END_TIME)
                .amount(TEST_BOOKING_AMOUNT)
                .note("Test")
                .status(BookingStatus.UPCOMING)
                .build();
        when(userService.getUserByEmailAndRole(mockBookingRequest.getEmail(), mockBookingRequest.getRole().name())).thenReturn(mockUser);
        when(servicesService.getServiceById(mockBookingRequest.getServiceId())).thenReturn(mockService);

        actualBooking = bookingServiceMock.save(mockBookingRequest);
        assertEquals(expectedBooking, actualBooking);
    }
}
