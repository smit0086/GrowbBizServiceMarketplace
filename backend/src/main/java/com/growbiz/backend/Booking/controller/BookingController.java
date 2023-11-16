package com.growbiz.backend.Booking.controller;

import com.growbiz.backend.Booking.helper.BookingControllerHelper;
import com.growbiz.backend.Booking.models.*;
import com.growbiz.backend.Booking.service.IBookingService;
import com.growbiz.backend.User.models.User;
import com.growbiz.backend.User.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private IBookingService bookingService;

    @Autowired
    private IUserService userService;

    @Autowired
    private BookingControllerHelper helper;

    @PostMapping(path = "/add")
    public ResponseEntity<BookingResponse> addBooking(@RequestBody BookingRequest bookingRequest) {
        Booking booking = bookingService.save(bookingRequest);
        return helper.createBookingResponse(List.of(booking));
    }

    @PutMapping(path = "/{bookingId}/status/")
    public ResponseEntity<BookingResponse> modifyBookingStatus(@PathVariable("bookingId") Long bookingId, @RequestParam String status) {
        Booking booking = bookingService.getBookingById(bookingId);

        booking.setStatus(BookingStatus.valueOf(status));
        bookingService.save(booking);

        return helper.createBookingResponse(List.of(booking));
    }

    @GetMapping(path = "/user/")
    public ResponseEntity<BookingResponse> getAllUserBookings(@RequestParam String email, @RequestParam String role) {
        User user = userService.getUserByEmailAndRole(email, role);
        List<Booking> bookings = bookingService.findByUserId(user.getId());

        return helper.createBookingResponse(bookings);
    }

    @GetMapping(path = "/user/upcoming/")
    public ResponseEntity<BookingResponse> getAllUpcomingUserBookings(
            @RequestParam String email,
            @RequestParam String role) {
        User user = userService.getUserByEmailAndRole(email, role);
        List<Booking> bookings = bookingService.getAllBookingsByUserIdAndStatus(user.getId(), "UPCOMING");

        return helper.createBookingResponse(bookings);
    }

    @GetMapping(path = "/user/completed/")
    public ResponseEntity<BookingResponse> getAllCompletedUserBookings(
            @RequestParam String email,
            @RequestParam String role) {
        User user = userService.getUserByEmailAndRole(email, role);
        List<Booking> bookings = bookingService.getAllBookingsByUserIdAndStatus(user.getId(), "COMPLETED");

        return helper.createBookingResponse(bookings);
    }

    @GetMapping(path = "/service/{serviceId}")
    public ResponseEntity<BookingResponse> getAllBookingsByService(@PathVariable Long serviceId) {
        List<Booking> bookings = bookingService.findByServiceId(serviceId);

        return helper.createBookingResponse(bookings);
    }

    @GetMapping(path = "/business/{businessId}")
    public ResponseEntity<BookingResponse> getAllBookingsByBusinessId(@PathVariable Long businessId) {
        List<Booking> bookings = bookingService.findByBusinessId(businessId);
        return helper.createBookingResponse(bookings);
    }

    @GetMapping(path = "/business/upcoming/{businessId}")
    public ResponseEntity<BookingResponse> getAllUpcomingBookingsByBusinessId(@PathVariable Long businessId) {
        List<Booking> bookings = bookingService.getAllBookingsByBusinessIdAndStatus(businessId, "UPCOMING");
        return helper.createBookingResponse(bookings);
    }

    @GetMapping(path = "/business/completed/{businessId}")
    public ResponseEntity<BookingResponse> getAllCompletedBookingsByBusinessId(@PathVariable Long businessId) {
        List<Booking> bookings = bookingService.getAllBookingsByBusinessIdAndStatus(businessId, "COMPLETED");
        return helper.createBookingResponse(bookings);
    }

    @GetMapping(path = "/getSlot/{businessId}/{serviceId}")
    public ResponseEntity<FreeSlotsResponse> getFreeTimeSlots(@PathVariable Long businessId, @PathVariable Long serviceId, @RequestParam("date") String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(dateString);
        return helper.createFreeSlotsResponse(bookingService.getFreeSlotsForWeek(businessId, date, serviceId));
    }
}
