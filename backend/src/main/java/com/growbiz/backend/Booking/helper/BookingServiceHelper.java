package com.growbiz.backend.Booking.helper;

import com.growbiz.backend.Booking.models.SlotRange;
import com.growbiz.backend.Business.model.BusinessHour;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

public class BookingServiceHelper {
    public List<SlotRange> getFreeSlots(Date date, BusinessHour businessHour) {
        List<SlotRange> freeSlots = new ArrayList<>();
        LocalTime start = null;
        LocalTime end = null;
        // TODO: get it from the serviceAPI
        long duration = 60;
        switch (DayOfWeek.valueOf(getDayOfWeek(date).toUpperCase())) {
            case MONDAY -> {
                if (Objects.isNull(businessHour.getMonday_start())) {
                    break;
                }
                start = businessHour.getMonday_start();
                end = businessHour.getMonday_end();
            }
            case TUESDAY -> {
                if (Objects.isNull(businessHour.getTuesday_start())) {
                    break;
                }
                start = businessHour.getTuesday_start();
                end = businessHour.getTuesday_end();
            }
            case WEDNESDAY -> {
                if (Objects.isNull(businessHour.getWednesday_start())) {
                    break;
                }
                start = businessHour.getWednesday_start();
                end = businessHour.getWednesday_end();
            }
            case THURSDAY -> {
                if (Objects.isNull(businessHour.getThursday_start())) {
                    break;
                }
                start = businessHour.getThursday_start();
                end = businessHour.getThursday_end();
            }
            case FRIDAY -> {
                if (Objects.isNull(businessHour.getFriday_start())) {
                    break;
                }
                start = businessHour.getFriday_start();
                end = businessHour.getFriday_end();
            }
            case SATURDAY -> {
                if (Objects.isNull(businessHour.getSaturday_start())) {
                    break;
                }
                start = businessHour.getSaturday_start();
                end = businessHour.getSaturday_end();
            }
            case SUNDAY -> {
                if (Objects.isNull(businessHour.getSunday_start())) {
                    break;
                }
                start = businessHour.getSunday_start();
                end = businessHour.getSunday_end();
            }
        }
        if (Objects.nonNull(start) && Objects.nonNull(end)) {
            return populateAllFreeSlots(start, end, duration);
        }
        return freeSlots;
    }

    private List<SlotRange> populateAllFreeSlots(LocalTime start, LocalTime end, long duration) {
        int timeSlotGap = 30;
        List<SlotRange> freeSlots = new ArrayList<>();
        // TODO: get booked slots from the booking API
        List<SlotRange> bookedSlots = new ArrayList<>();
        int availableTime = 0;
        end = end.minusMinutes(timeSlotGap);
        while (end.isAfter(start) || end.equals(start)) {
            LocalTime streamEnd = end;
            Optional<SlotRange> optional = bookedSlots.stream().filter(slot -> (slot.getStartTime().isBefore(streamEnd) && slot.getEndTime().isAfter(streamEnd)) || slot.getStartTime().equals(streamEnd)).findFirst();
            if (optional.isPresent()) {
                availableTime = 0;

            } else {
                availableTime += timeSlotGap;
                if (availableTime >= duration) {
                    freeSlots.add(new SlotRange(end, end.plusMinutes(duration)));
                }
            }
            end = end.minusMinutes(timeSlotGap);
        }
        freeSlots.sort(Comparator.comparing(SlotRange::getStartTime));
        return freeSlots;
    }

    private String getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return new SimpleDateFormat("EEEE").format(cal.getTime());
    }
}
