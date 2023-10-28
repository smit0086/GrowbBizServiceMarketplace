package com.growbiz.backend.Booking.helper;

import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Booking.models.SlotRange;
import com.growbiz.backend.Business.model.BusinessHour;
import com.growbiz.backend.Services.service.IServicesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceHelper {

    @Autowired
    private final IServicesService servicesService;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * This gets all the dates of the current week
     *
     * @param date - any date in the current week
     * @return - List of all dates
     * @author - an370985@dal.ca
     */
    public List<Date> getCurrentWeekAllDates(Date date) {
        List<Date> dateList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, Calendar.MONDAY - dayOfWeek);
        Date startOfWeek = calendar.getTime();
        calendar.add(Calendar.DATE, 6);
        Date endOfWeek = calendar.getTime();
        calendar.setTime(startOfWeek);
        while (!calendar.getTime().after(endOfWeek)) {
            dateList.add(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }
        return dateList;
    }

    /**
     * This gives all the freeSlot ranges for the given date
     *
     * @param date         - date for which freeSlots to be returned
     * @param businessHour - this will give the businessHour start and end time for the currentDate
     * @return - list of freeSlots
     * @author - an370985@dal.ca
     */
    public List<SlotRange> getFreeSlots(Date date, BusinessHour businessHour, Long serviceId, List<Booking> bookingList) {
        List<SlotRange> freeSlots = new ArrayList<>();
        LocalTime start = null;
        LocalTime end = null;

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
            return populateAllFreeSlots(start, end,
                    Duration.between(LocalTime.of(0, 0), servicesService.getServiceById(serviceId).getTimeRequired()).toMinutes(),
                    bookingList.stream().filter(booking -> sdf.format(date).equals(sdf.format(booking.getDate()))).toList());
        }
        return freeSlots;
    }

    /**
     * Algorithm to populate all the free slots
     *
     * @param start
     * @param end
     * @param duration
     * @param bookingList
     * @return
     * @author - an370985@dal.ca
     */
    private List<SlotRange> populateAllFreeSlots(LocalTime start, LocalTime end, long duration, List<Booking> bookingList) {
        int timeSlotGap = 30;
        List<SlotRange> freeSlots = new ArrayList<>();
        List<SlotRange> bookedSlots = new ArrayList<>();
        bookingList.forEach(booking -> bookedSlots.add(new SlotRange(booking.getStartTime(), booking.getEndTime())));
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
