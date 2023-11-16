package com.growbiz.backend.Booking;

import com.growbiz.backend.Booking.controller.BookingController;
import com.growbiz.backend.Booking.helper.BookingControllerHelper;
import com.growbiz.backend.Booking.models.*;
import com.growbiz.backend.Booking.service.IBookingService;
import com.growbiz.backend.Business.model.Business;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.User.models.Role;
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
    private static final LocalTime TEST_BOOKING_START_TIME = LocalTime.of(11, 30);
    private static final LocalTime TEST_BOOKING_END_TIME = LocalTime.of(12, 30);

    @Mock
    private IBookingService bookingService;

    @Mock
    private IUserService userService;

    @Mock
    private BookingControllerHelper bookingHelper;

    @InjectMocks
    private BookingController bookingController;

    @Mock
    BookingRequest mockBookingRequest = BookingRequest
            .builder()
            .serviceId(1L)
            .date(TEST_BOOKING_DATE)
            .startTime(TEST_BOOKING_START_TIME)
            .endTime(TEST_BOOKING_END_TIME)
            .amount(120.50)
            .note("Test")
            .status(BookingStatus.UPCOMING)
            .build();

    @Mock
    User mockUser = User
            .builder()
            .id(1L)
            .email("test@dal.ca")
            .password("test")
            .firstName("John")
            .lastName("Doe")
            .role(Role.CUSTOMER)
            .build();

    @Mock
    Business mockBusiness = Business
            .builder()
            .businessId(1L)
            .businessName("Test Business")
            .build();
    @Mock
    SubCategory mockSubCategory = SubCategory
            .builder()
            .subCategoryID(1L)
            .name("Test SubCategory")
            .build();

    @Mock
    Services mockService = Services
            .builder()
            .serviceId(1L)
            .serviceName("Test Service")
            .description("Test")
            .price(24.00)
            .timeRequired(LocalTime.of(0, 30))
            .business(mockBusiness)
            .subCategory(mockSubCategory)
            .build();

    @Mock
    Booking mockBooking = Booking
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

    @Mock
    BookingBusiness mockBookingBusiness = BookingBusiness.builder()
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

    @Mock
    Booking mockOngoingBooking = Booking.builder()
            .id(2L)
            .user(mockUser)
            .service(mockService)
            .date("2023-11-13")
            .startTime(TEST_BOOKING_START_TIME)
            .endTime(TEST_BOOKING_END_TIME)
            .amount(120.50)
            .note("Test")
            .status(BookingStatus.ONGOING)
            .build();

    @Mock
    Booking mockCompletedBooking = Booking
            .builder()
            .id(3L)
            .user(mockUser)
            .service(mockService)
            .date("2023-11-01")
            .startTime(TEST_BOOKING_START_TIME)
            .endTime(TEST_BOOKING_END_TIME)
            .amount(120.50)
            .note("Test")
            .status(BookingStatus.COMPLETED)
            .build();

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
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
    public void updateBookingStatusToCompletedTest() {
        ResponseEntity<BookingResponse> actualResponse;
        Booking updateBooking = Booking.builder()
                .id(2L)
                .user(mockUser)
                .service(mockService)
                .date("2023-11-13")
                .startTime(TEST_BOOKING_START_TIME)
                .endTime(TEST_BOOKING_END_TIME)
                .amount(120.50)
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

        when(bookingService.getBookingById(2L)).thenReturn(mockOngoingBooking);
        when(bookingHelper.createBookingResponse(List.of(mockOngoingBooking))).thenReturn(expectedResponse);

        actualResponse = bookingController.modifyBookingStatus(2L, "COMPLETED");
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

        when(bookingService.getAllBookingsByBusinessIdAndStatus(1L, BookingStatus.UPCOMING)).thenReturn(List.of(mockBooking));
        when(bookingHelper.convertToBookingBusinessList(List.of(mockBooking))).thenReturn(List.of(mockBookingBusiness));
        when(bookingHelper.createBookingBusinessResponse(List.of(mockBookingBusiness))).thenReturn(expectedResponse);

        actualResponse = bookingController.getAllUpcomingBookingsByBusinessId(1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getAllCompletedBookingsForBusinessTest() {
        ResponseEntity<BookingBusinessResponse> actualResponse;
        BookingBusiness mockBookingBusiness = BookingBusiness.builder()
                .id(1L)
                .date(mockCompletedBooking.getDate())
                .startTime(mockCompletedBooking.getStartTime())
                .endTime(mockCompletedBooking.getEndTime())
                .amount(mockCompletedBooking.getAmount())
                .note(mockCompletedBooking.getNote())
                .status(mockCompletedBooking.getStatus())
                .userEmail("test@dal.ca")
                .serviceName("Test Service")
                .timeRequired(LocalTime.of(0, 30))
                .build();
        ResponseEntity<BookingBusinessResponse> expectedResponse = ResponseEntity.ok(
                BookingBusinessResponse.builder()
                        .bookings(List.of(mockBookingBusiness))
                        .subject("test@dal.ca")
                        .role(Role.CUSTOMER)
                        .build()
        );


        when(bookingService.getAllBookingsByBusinessIdAndStatus(1L, BookingStatus.COMPLETED)).thenReturn(List.of(mockCompletedBooking));
        when(bookingHelper.convertToBookingBusinessList(List.of(mockCompletedBooking))).thenReturn(List.of(mockBookingBusiness));
        when(bookingHelper.createBookingBusinessResponse(List.of(mockBookingBusiness))).thenReturn(expectedResponse);

        actualResponse = bookingController.getAllCompletedBookingsByBusinessId(1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getAllOngoingBookingsForBusinessTest() {
        ResponseEntity<BookingBusinessResponse> actualResponse;
        BookingBusiness mockBookingBusiness = BookingBusiness.builder()
                .id(1L)
                .date(mockCompletedBooking.getDate())
                .startTime(mockCompletedBooking.getStartTime())
                .endTime(mockCompletedBooking.getEndTime())
                .amount(mockCompletedBooking.getAmount())
                .note(mockCompletedBooking.getNote())
                .status(mockCompletedBooking.getStatus())
                .userEmail("test@dal.ca")
                .serviceName("Test Service")
                .timeRequired(LocalTime.of(0, 30))
                .build();
        ResponseEntity<BookingBusinessResponse> expectedResponse = ResponseEntity.ok(
                BookingBusinessResponse.builder()
                        .bookings(List.of(mockBookingBusiness))
                        .subject("test@dal.ca")
                        .role(Role.CUSTOMER)
                        .build()
        );


        when(bookingService.getAllBookingsByBusinessIdAndStatus(1L, BookingStatus.ONGOING)).thenReturn(List.of(mockOngoingBooking));
        when(bookingHelper.convertToBookingBusinessList(List.of(mockOngoingBooking))).thenReturn(List.of(mockBookingBusiness));
        when(bookingHelper.createBookingBusinessResponse(List.of(mockBookingBusiness))).thenReturn(expectedResponse);

        actualResponse = bookingController.getAllOngoingBookingsByBusinessId(1L);
        assertEquals(expectedResponse, actualResponse);
    }
}
