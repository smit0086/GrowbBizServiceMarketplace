package com.growbiz.backend.Booking.service;

import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Enums.BookingStatus;
import com.growbiz.backend.RequestResponse.Booking.BookingRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IBookingService {

    Booking findById(Long id);

    List<Booking> findByServiceId(Long serviceId);

    List<Booking> findByUserId(Long userId);

    List<Booking> findByBusinessId(Long businessId);

    Booking save(Booking booking);

    Booking save(BookingRequest bookingRequest);

    List<Booking> getAllBookingsByUserIdAndStatus(Long userId, String status);

    List<Booking> getAllBookingsByBusinessIdAndStatus(Long businessId, BookingStatus status);
}
