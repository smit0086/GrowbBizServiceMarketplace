package com.growbiz.backend.Booking;

import com.growbiz.backend.Booking.controller.BookingController;
import com.growbiz.backend.Booking.helper.BookingControllerHelper;
import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Booking.models.BookingBusiness;
import com.growbiz.backend.Booking.service.IBookingService;
import com.growbiz.backend.Business.models.Business;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Enums.BookingStatus;
import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.RequestResponse.Booking.BookingBusinessResponse;
import com.growbiz.backend.RequestResponse.Booking.BookingRequest;
import com.growbiz.backend.RequestResponse.Booking.BookingResponse;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.User.models.User;
import com.growbiz.backend.User.service.IUserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {

    private static final String TEST_BOOKING_DATE = "2023-11-13";
    public static final int TEST_SERVICE_TIME_REQ_MIN = 30;
    private static final LocalTime TEST_BOOKING_START_TIME = LocalTime.of(11, TEST_SERVICE_TIME_REQ_MIN);
    private static final LocalTime TEST_BOOKING_END_TIME = LocalTime.of(12, TEST_SERVICE_TIME_REQ_MIN);
    public static final long TEST_ID = 1L;
    public static final double TEST_BOOKING_AMOUNT = 120.50;
    public static final double TEST_SERVICE_PRICE = 24.00;
    public static final int TEST_SERVICE_TIME_REQ_HR = 0;
    public static final long TEST_ONG_BKNG_ID = 2L;
    public static final long TEST_COMP_BKNG_ID = 3L;

    @Mock
    private IBookingService bookingService;

    @Mock
    private IUserService userService;

    @Mock
    private BookingControllerHelper bookingHelper;

    @InjectMocks
    private BookingController bookingController;

    @Mock
    BookingRequest mockBookingRequest;

    @Mock
    User mockUser;

    @Mock
    Business mockBusiness;

    @Mock
    SubCategory mockSubCategory;

    @Mock
    Services mockService;

    @Mock
    Booking mockBooking;

    @Mock
    BookingBusiness mockBookingBusiness;

    @Mock
    Booking mockOngoingBooking;

    @Mock
    Booking mockCompletedBooking;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);

        mockBookingRequest = BookingRequest
                .builder()
                .serviceId(TEST_ID)
                .date(TEST_BOOKING_DATE)
                .startTime(TEST_BOOKING_START_TIME)
                .endTime(TEST_BOOKING_END_TIME)
                .amount(TEST_BOOKING_AMOUNT)
                .note("Test")
                .status(BookingStatus.UPCOMING)
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

        mockBookingBusiness = BookingBusiness.builder()
                .id(TEST_ID)
                .date(TEST_BOOKING_DATE)
                .startTime(TEST_BOOKING_START_TIME)
                .endTime(TEST_BOOKING_END_TIME)
                .amount(TEST_BOOKING_AMOUNT)
                .note("Test")
                .status(BookingStatus.UPCOMING)
                .userEmail("test@dal.ca")
                .serviceName("Test Service")
                .timeRequired(LocalTime.of(TEST_SERVICE_TIME_REQ_HR, TEST_SERVICE_TIME_REQ_MIN))
                .build();

        mockOngoingBooking = Booking.builder()
                .id(TEST_ONG_BKNG_ID)
                .user(mockUser)
                .service(mockService)
                .date("2023-11-13")
                .startTime(TEST_BOOKING_START_TIME)
                .endTime(TEST_BOOKING_END_TIME)
                .amount(TEST_BOOKING_AMOUNT)
                .note("Test")
                .status(BookingStatus.ONGOING)
                .build();

        mockCompletedBooking = Booking
                .builder()
                .id(TEST_COMP_BKNG_ID)
                .user(mockUser)
                .service(mockService)
                .date("2023-11-01")
                .startTime(TEST_BOOKING_START_TIME)
                .endTime(TEST_BOOKING_END_TIME)
                .amount(TEST_BOOKING_AMOUNT)
                .note("Test")
                .status(BookingStatus.COMPLETED)
                .build();
    }

    @Test
    public void addBookingSuccessTest() {
        ResponseEntity<BookingResponse> actualResponse;
        ResponseEntity<BookingResponse> expectedResponse = ResponseEntity.ok(
                BookingResponse.builder()
                        .bookings(List.of(mockBooking))
                        .subject(mockUser.getEmail())
                        .role(Role.CUSTOMER)
                        .build()
        );

        when(bookingService.save(mockBookingRequest)).thenReturn(mockBooking);
        when(bookingHelper.createBookingResponse(List.of(mockBooking))).thenReturn(expectedResponse);

        actualResponse = bookingController.addBooking(mockBookingRequest);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getAllBookingsForUserTest() {
        ResponseEntity<BookingResponse> actualResponse;
        ResponseEntity<BookingResponse> expectedResponse = ResponseEntity.ok(
                BookingResponse.builder()
                        .bookings(List.of(mockBooking, mockCompletedBooking, mockOngoingBooking))
                        .subject("test@dal.ca")
                        .role(Role.CUSTOMER)
                        .build()
        );

        when(userService.getUserByEmailAndRole("test@dal.ca", Role.CUSTOMER.name())).thenReturn(mockUser);
        when(bookingService.findByUserId(mockUser.getId())).thenReturn(List.of(mockBooking, mockCompletedBooking, mockOngoingBooking));
        when(bookingHelper.createBookingResponse(List.of(mockBooking, mockCompletedBooking, mockOngoingBooking))).thenReturn(expectedResponse);

        actualResponse = bookingController.getAllUserBookings("test@dal.ca", "CUSTOMER");
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getAllUpcomingBookingsForUserTest() {
        ResponseEntity<BookingResponse> actualResponse;
        ResponseEntity<BookingResponse> expectedResponse = ResponseEntity.ok(
                BookingResponse.builder()
                        .bookings(List.of(mockBooking))
                        .subject("test@dal.ca")
                        .role(Role.CUSTOMER)
                        .build()
        );

        when(bookingService.getAllBookingsByUserIdAndStatus(mockUser.getId(), "UPCOMING")).thenReturn(List.of(mockBooking));
        when(userService.getUserByEmailAndRole("test@dal.ca", Role.CUSTOMER.name())).thenReturn(mockUser);
        when(bookingHelper.createBookingResponse(List.of(mockBooking))).thenReturn(expectedResponse);

        actualResponse = bookingController.getAllUpcomingUserBookings("test@dal.ca", "CUSTOMER");
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getAllCompletedBookingsForUserTest() {
        ResponseEntity<BookingResponse> actualResponse;
        ResponseEntity<BookingResponse> expectedResponse = ResponseEntity.ok(
                BookingResponse.builder()
                        .bookings(List.of(mockCompletedBooking))
                        .subject("test@dal.ca")
                        .role(Role.CUSTOMER)
                        .build()
        );

        when(bookingService.getAllBookingsByUserIdAndStatus(mockUser.getId(), "COMPLETED")).thenReturn(List.of(mockCompletedBooking));
        when(userService.getUserByEmailAndRole("test@dal.ca", Role.CUSTOMER.name())).thenReturn(mockUser);
        when(bookingHelper.createBookingResponse(List.of(mockCompletedBooking))).thenReturn(expectedResponse);

        actualResponse = bookingController.getAllCompletedUserBookings("test@dal.ca", "CUSTOMER");
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getAllBookingsForServiceIdTest() {
        ResponseEntity<BookingResponse> actualResponse;
        ResponseEntity<BookingResponse> expectedResponse = ResponseEntity.ok(
                BookingResponse.builder()
                        .bookings(List.of(mockBooking, mockCompletedBooking, mockOngoingBooking))
                        .subject("test@dal.ca")
                        .role(Role.CUSTOMER)
                        .build()
        );

        when(bookingService.findByServiceId(TEST_ID)).thenReturn(List.of(mockBooking, mockCompletedBooking, mockOngoingBooking));
        when(bookingHelper.createBookingResponse(List.of(mockBooking, mockCompletedBooking, mockOngoingBooking))).thenReturn(expectedResponse);

        actualResponse = bookingController.getAllBookingsByService(TEST_ID);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getAllBookingsForBusinessIdTest() {
        ResponseEntity<BookingResponse> actualResponse;
        ResponseEntity<BookingResponse> expectedResponse = ResponseEntity.ok(
                BookingResponse.builder()
                        .bookings(List.of(mockBooking, mockCompletedBooking, mockOngoingBooking))
                        .subject("test@dal.ca")
                        .role(Role.CUSTOMER)
                        .build()
        );

        when(bookingService.findByBusinessId(TEST_ID)).thenReturn(List.of(mockBooking, mockCompletedBooking, mockOngoingBooking));
        when(bookingHelper.createBookingResponse(List.of(mockBooking, mockCompletedBooking, mockOngoingBooking))).thenReturn(expectedResponse);

        actualResponse = bookingController.getAllBookingsByBusinessId(TEST_ID);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void updateBookingStatusToCompletedTest() {
        ResponseEntity<BookingResponse> actualResponse;
        Booking updateBooking = Booking.builder()
                .id(TEST_ONG_BKNG_ID)
                .user(mockUser)
                .service(mockService)
                .date("2023-11-13")
                .startTime(TEST_BOOKING_START_TIME)
                .endTime(TEST_BOOKING_END_TIME)
                .amount(TEST_BOOKING_AMOUNT)
                .note("Test")
                .status(BookingStatus.COMPLETED)
                .build();
        ResponseEntity<BookingResponse> expectedResponse = ResponseEntity.ok(
                BookingResponse.builder()
                        .bookings(List.of(updateBooking))
                        .subject("test@dal.ca")
                        .role(Role.CUSTOMER)
                        .build()
        );

        when(bookingService.findById(TEST_ONG_BKNG_ID)).thenReturn(mockOngoingBooking);
        when(bookingHelper.createBookingResponse(List.of(mockOngoingBooking))).thenReturn(expectedResponse);

        actualResponse = bookingController.modifyBookingStatus(TEST_ONG_BKNG_ID, "COMPLETED");
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getAllUpcomingBookingsForBusinessTest() {
        ResponseEntity<BookingBusinessResponse> actualResponse;
        ResponseEntity<BookingBusinessResponse> expectedResponse = ResponseEntity.ok(
                BookingBusinessResponse.builder()
                        .bookings(List.of(mockBookingBusiness))
                        .subject("test@dal.ca")
                        .role(Role.CUSTOMER)
                        .build()
        );

        when(bookingService.getAllBookingsByBusinessIdAndStatus(TEST_ID, BookingStatus.UPCOMING)).thenReturn(List.of(mockBooking));
        when(bookingHelper.convertToBookingBusinessList(List.of(mockBooking))).thenReturn(List.of(mockBookingBusiness));
        when(bookingHelper.createBookingBusinessResponse(List.of(mockBookingBusiness))).thenReturn(expectedResponse);

        actualResponse = bookingController.getAllUpcomingBookingsByBusinessId(TEST_ID);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getAllCompletedBookingsForBusinessTest() {
        ResponseEntity<BookingBusinessResponse> actualResponse;
        BookingBusiness mockBookingBusiness = BookingBusiness.builder()
                .id(TEST_ID)
                .date(mockCompletedBooking.getDate())
                .startTime(mockCompletedBooking.getStartTime())
                .endTime(mockCompletedBooking.getEndTime())
                .amount(mockCompletedBooking.getAmount())
                .note(mockCompletedBooking.getNote())
                .status(mockCompletedBooking.getStatus())
                .userEmail("test@dal.ca")
                .serviceName("Test Service")
                .timeRequired(LocalTime.of(TEST_SERVICE_TIME_REQ_HR, TEST_SERVICE_TIME_REQ_MIN))
                .build();
        ResponseEntity<BookingBusinessResponse> expectedResponse = ResponseEntity.ok(
                BookingBusinessResponse.builder()
                        .bookings(List.of(mockBookingBusiness))
                        .subject("test@dal.ca")
                        .role(Role.CUSTOMER)
                        .build()
        );


        when(bookingService.getAllBookingsByBusinessIdAndStatus(TEST_ID, BookingStatus.COMPLETED)).thenReturn(List.of(mockCompletedBooking));
        when(bookingHelper.convertToBookingBusinessList(List.of(mockCompletedBooking))).thenReturn(List.of(mockBookingBusiness));
        when(bookingHelper.createBookingBusinessResponse(List.of(mockBookingBusiness))).thenReturn(expectedResponse);

        actualResponse = bookingController.getAllCompletedBookingsByBusinessId(TEST_ID);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getAllOngoingBookingsForBusinessTest() {
        ResponseEntity<BookingBusinessResponse> actualResponse;
        BookingBusiness mockBookingBusiness = BookingBusiness.builder()
                .id(TEST_ID)
                .date(mockCompletedBooking.getDate())
                .startTime(mockCompletedBooking.getStartTime())
                .endTime(mockCompletedBooking.getEndTime())
                .amount(mockCompletedBooking.getAmount())
                .note(mockCompletedBooking.getNote())
                .status(mockCompletedBooking.getStatus())
                .userEmail("test@dal.ca")
                .serviceName("Test Service")
                .timeRequired(LocalTime.of(TEST_SERVICE_TIME_REQ_HR, TEST_SERVICE_TIME_REQ_MIN))
                .build();
        ResponseEntity<BookingBusinessResponse> expectedResponse = ResponseEntity.ok(
                BookingBusinessResponse.builder()
                        .bookings(List.of(mockBookingBusiness))
                        .subject("test@dal.ca")
                        .role(Role.CUSTOMER)
                        .build()
        );


        when(bookingService.getAllBookingsByBusinessIdAndStatus(TEST_ID, BookingStatus.ONGOING)).thenReturn(List.of(mockOngoingBooking));
        when(bookingHelper.convertToBookingBusinessList(List.of(mockOngoingBooking))).thenReturn(List.of(mockBookingBusiness));
        when(bookingHelper.createBookingBusinessResponse(List.of(mockBookingBusiness))).thenReturn(expectedResponse);

        actualResponse = bookingController.getAllOngoingBookingsByBusinessId(TEST_ID);
        assertEquals(expectedResponse, actualResponse);
    }
}
