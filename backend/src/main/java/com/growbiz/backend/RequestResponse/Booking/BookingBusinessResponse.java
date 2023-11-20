package com.growbiz.backend.RequestResponse.Booking;

import com.growbiz.backend.Booking.models.BookingBusiness;
import com.growbiz.backend.RequestResponse.Basic.BasicResponse;
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
