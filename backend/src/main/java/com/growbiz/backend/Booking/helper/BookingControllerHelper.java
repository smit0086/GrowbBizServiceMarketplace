package com.growbiz.backend.Booking.helper;

import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Booking.models.BookingResponse;
import com.growbiz.backend.Booking.models.FreeSlotsResponse;
import com.growbiz.backend.Booking.models.SlotRange;
import com.growbiz.backend.User.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    public ResponseEntity<FreeSlotsResponse> createFreeSlotsResponse(Map<Date, List<SlotRange>> freeSlots) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(FreeSlotsResponse.builder()
                .freeSlots(freeSlots)
                .subject(user.getEmail())
                .role(user.getRole())
                .build());
    }
}
