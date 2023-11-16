package com.growbiz.backend.Booking.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingBusiness {
    private Long id;

    private String date;

    private LocalTime startTime;

    private LocalTime endTime;

    private double amount;

    private String note;

    private BookingStatus status;

    private String userEmail;

    private String serviceName;

    private LocalTime timeRequired;
}
