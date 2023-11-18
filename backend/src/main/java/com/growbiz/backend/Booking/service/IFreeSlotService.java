package com.growbiz.backend.Booking.service;

import com.growbiz.backend.Booking.models.SlotRange;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public interface IFreeSlotService {
    Map<Date, List<SlotRange>> getFreeSlotsForWeek(Long businessId, Date date, Long serviceId);
}
