package com.growbiz.backend.Booking.helper;

import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Booking.models.BookingBusiness;
import com.growbiz.backend.BusinessHour.model.BusinessHour;
import com.growbiz.backend.FreeSlot.models.FreeSlotsResponse;
import com.growbiz.backend.FreeSlot.models.SlotRange;
import com.growbiz.backend.Responses.Booking.BookingBusinessResponse;
import com.growbiz.backend.Responses.Booking.BookingResponse;
import com.growbiz.backend.Responses.BusinessHour.BusinessHourResponse;
import com.growbiz.backend.User.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    public ResponseEntity<BookingBusinessResponse> createBookingBusinessResponse(List<BookingBusiness> bookingList) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(BookingBusinessResponse.builder()
                .bookings(bookingList)
                .subject(user.getEmail())
                .role(user.getRole())
                .build());
    }

    public List<BookingBusiness> convertToBookingBusinessList(List<Booking> bookings) {
        BookingBusiness bookingBusiness;
        List<BookingBusiness> bookingBusinesses = new ArrayList<>();

        for (Booking booking : bookings) {
            bookingBusiness = BookingBusiness.builder()
                    .id(booking.getId())
                    .date(booking.getDate())
                    .startTime(booking.getStartTime())
                    .endTime(booking.getEndTime())
                    .amount(booking.getAmount())
                    .note(booking.getNote())
                    .status(booking.getStatus())
                    .userEmail(booking.getUser().getEmail())
                    .serviceName(booking.getService().getServiceName())
                    .timeRequired(booking.getService().getTimeRequired())
                    .build();

            bookingBusinesses.add(bookingBusiness);
        }

        return bookingBusinesses;
    }

    public ResponseEntity<BusinessHourResponse> createBusinessHourResponse(BusinessHour businessHour) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(BusinessHourResponse.builder().businessHour(businessHour).subject(user.getEmail())
                .role(user.getRole())
                .build());
    }
}
