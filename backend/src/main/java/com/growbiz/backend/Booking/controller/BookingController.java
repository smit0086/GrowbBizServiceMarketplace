package com.growbiz.backend.Booking.controller;

import com.growbiz.backend.Booking.helper.BookingControllerHelper;
import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Booking.models.BookingRequest;
import com.growbiz.backend.Booking.models.BookingResponse;
import com.growbiz.backend.Booking.service.IBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private IBookingService bookingService;

    @Autowired
    private BookingControllerHelper helper;

    @PostMapping(path = "/add")
    public ResponseEntity<BookingResponse> addBooking(@RequestBody BookingRequest bookingRequest) {
        Booking booking = bookingService.save(bookingRequest);

        return helper.createBookingResponse(List.of(booking));
    }

    @GetMapping(path = "/user/{id}")
    public ResponseEntity<BookingResponse> getAllUserBookings(@PathVariable Long id) {
        List<Booking> bookings = bookingService.findByUserId(id);

        return helper.createBookingResponse(bookings);
    }

    @GetMapping(path = "/service/{serviceId}")
    public ResponseEntity<BookingResponse> getAllBookingsByService(@PathVariable Long serviceId) {
        List<Booking> bookings = bookingService.findByServiceId(serviceId);

        return helper.createBookingResponse(bookings);
    }
}
