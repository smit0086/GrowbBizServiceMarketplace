package com.growbiz.backend.Booking;

import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Booking.models.BookingStatus;
import com.growbiz.backend.Booking.repository.IBookingRepository;
import com.growbiz.backend.Booking.service.BookingService;
import com.growbiz.backend.Business.model.Business;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Exception.exceptions.BookingNotFoundException;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.User.models.Role;
import com.growbiz.backend.User.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
    private static final LocalTime TEST_BOOKING_START_TIME = LocalTime.of(11, 30);
    private static final LocalTime TEST_BOOKING_END_TIME = LocalTime.of(12, 30);

    @InjectMocks
    BookingService bookingServiceMock;

    @Mock
    IBookingRepository bookingRepository;

    @Mock
    User mockUser;

    @Mock
    Business mockBusiness;

    @Mock
    SubCategory mockSubCategory;

    Services mockService;

    Booking mockBooking;

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
    }

    @Test
    public void getBookingByBookingIdSuccessTest() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(mockBooking));
        Booking actualBooking = bookingServiceMock.findById(1L);
        assertEquals(mockBooking, actualBooking);
    }

    @Test
    public void getBookingByBookingIdFailureTest() {
        assertThrows(BookingNotFoundException.class, () -> bookingServiceMock.findById(2L));
    }

    @Test
    public void getBookingByServiceIdTest() {
        when(bookingRepository.findByServiceServiceId(1L)).thenReturn(List.of(mockBooking));
        List<Booking> actualBooking = bookingServiceMock.findByServiceId(1L);
        assertEquals(List.of(mockBooking), actualBooking);
    }

    @Test
    public void getBookingByUserIdTest() {
        when(bookingRepository.findByUserId(1L)).thenReturn(List.of(mockBooking));
        List<Booking> actualBooking = bookingServiceMock.findByUserId(1L);
        assertEquals(List.of(mockBooking), actualBooking);
    }

    @Test
    public void getBookingByUserIdAndStatusTest() {
        when(bookingRepository.findByUserIdAndStatus(1L, BookingStatus.UPCOMING)).thenReturn(List.of(mockBooking));
        List<Booking> actualBooking = bookingServiceMock.getAllBookingsByUserIdAndStatus(1L, "UPCOMING");
        assertEquals(List.of(mockBooking), actualBooking);
    }

    @Test
    public void getBookingByBusinessIdTest() {
        when(bookingRepository.findByServiceBusinessBusinessId(1L)).thenReturn(List.of(mockBooking));
        List<Booking> actualBooking = bookingServiceMock.findByBusinessId(1L);
        assertEquals(List.of(mockBooking), actualBooking);
    }

    @Test
    public void getBookingByBusinessIdAndStatusTest() {
        when(bookingRepository.findByServiceBusinessBusinessIdAndStatus(1L, BookingStatus.UPCOMING)).thenReturn(List.of(mockBooking));
        List<Booking> actualBooking = bookingServiceMock.getAllBookingsByBusinessIdAndStatus(1L, BookingStatus.UPCOMING);
        assertEquals(List.of(mockBooking), actualBooking);
    }

    @Test
    public void saveBookingTest() {
        bookingServiceMock.save(mockBooking);
    }
}
