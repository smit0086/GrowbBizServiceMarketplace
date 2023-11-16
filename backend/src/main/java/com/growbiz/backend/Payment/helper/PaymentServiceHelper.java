package com.growbiz.backend.Payment.helper;

import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Booking.models.BookingStatus;
import com.growbiz.backend.Booking.service.IBookingService;
import com.growbiz.backend.Payment.model.Payment;
import com.growbiz.backend.User.models.Role;
import com.growbiz.backend.User.models.User;
import com.growbiz.backend.User.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

public class PaymentServiceHelper {
    @Autowired
    IBookingService bookingService;
    @Autowired
    IUserService userService;

    public void saveToBooking(Payment payment, Long amount) {
        User user = userService.getUserByEmailAndRole(payment.getUserEmail(), Role.CUSTOMER.name());
        Booking booking = Booking.builder()
                .amount((double) amount)
                .date(payment.getDate())
                .endTime(payment.getEndTime())
                .startTime(payment.getStartTime())
                .note(payment.getNote())
                .user(user)
                .status(BookingStatus.UPCOMING)
                .build();
        bookingService.save(booking);
    }
}
