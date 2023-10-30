package com.growbiz.backend.Booking.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.growbiz.backend.Responses.model.BasicRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;
import java.util.Date;


@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BookingRequest extends BasicRequest {

    private Long userId;

    private Long serviceId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date date;

    private LocalTime startTime;

    private LocalTime endTime;
}
