package com.growbiz.backend.Exception.exceptions.Booking;

public class BookingNotFoundException extends RuntimeException {

    public BookingNotFoundException(String message) {
        super(message);
    }
}
