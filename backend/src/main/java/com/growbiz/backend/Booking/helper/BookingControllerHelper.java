package com.growbiz.backend.Booking.helper;

import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Booking.models.BookingResponse;
import com.growbiz.backend.User.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingControllerHelper {

    public ResponseEntity<BookingResponse> createBookingResponse(List<Booking> bookingList) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(BookingResponse.builder()
                .bookings(bookingList)
                .subject(user.getEmail())
                .role(user.getRole())
                .build());
    }
}
