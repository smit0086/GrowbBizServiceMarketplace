package com.growbiz.backend.Booking.service;

import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Booking.models.BookingRequest;
import com.growbiz.backend.Booking.models.SlotRange;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public interface IBookingService {

    Booking getBookingById(Long id);

    List<Booking> findByServiceId(Long serviceId);

    List<Booking> findByUserId(Long userId);

    void save(Booking booking);

    Booking save(BookingRequest bookingRequest);

    Map<Date, List<SlotRange>> getFreeSlotsForWeek(Long businessId, Date date);
}
