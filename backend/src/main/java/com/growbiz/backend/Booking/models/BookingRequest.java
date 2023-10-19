package com.growbiz.backend.Booking.models;

import com.growbiz.backend.Responses.model.BasicRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BookingRequest extends BasicRequest {

    private Long userId;

    private Long serviceId;

    private Date startTime;

    private Date endTime;
}
