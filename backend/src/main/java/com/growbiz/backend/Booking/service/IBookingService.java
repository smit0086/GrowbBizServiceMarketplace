package com.growbiz.backend.Booking.service;

import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Booking.models.BookingRequest;
import com.growbiz.backend.Booking.models.BookingStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IBookingService {

    Booking getBookingById(Long id);

    List<Booking> findByServiceId(Long serviceId);

    List<Booking> findByUserId(Long userId);

    List<Booking> findByBusinessId(Long businessId);

    void save(Booking booking);

    Booking save(BookingRequest bookingRequest);

    List<Booking> getAllBookingsByUserIdAndStatus(Long userId, String status);

    List<Booking> getAllBookingsByBusinessIdAndStatus(Long businessId, BookingStatus status);
}
