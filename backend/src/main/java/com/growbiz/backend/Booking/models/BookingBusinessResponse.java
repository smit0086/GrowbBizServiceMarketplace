package com.growbiz.backend.Booking.models;

import com.growbiz.backend.Responses.model.BasicResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class BookingBusinessResponse extends BasicResponse {
    private List<BookingBusiness> bookings;
}
