package com.growbiz.backend.Responses.Booking;

import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Responses.Basic.BasicResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class BookingResponse extends BasicResponse {

    private List<Booking> bookings;
}
