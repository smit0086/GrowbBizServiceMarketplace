package com.growbiz.backend.Booking.models;

import com.growbiz.backend.Responses.model.BasicRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;


@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BookingRequest extends BasicRequest {

    private Long serviceId;

    private String date;

    private LocalTime startTime;

    private LocalTime endTime;

    private double amount;

    private String note;
}
