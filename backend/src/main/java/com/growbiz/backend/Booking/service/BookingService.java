package com.growbiz.backend.Booking.service;

import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Booking.models.BookingRequest;
import com.growbiz.backend.Booking.models.SlotRange;
import com.growbiz.backend.Booking.repository.IBookingRepository;
import com.growbiz.backend.Business.model.BusinessHour;
import com.growbiz.backend.Business.service.IBusinessHourService;
import com.growbiz.backend.Exception.exceptions.BookingNotFoundException;
import com.growbiz.backend.User.models.User;
import com.growbiz.backend.User.service.IUserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService implements IBookingService {

    @Autowired
    private final IBookingRepository bookingRepository;

    @Autowired
    private final IUserService userService;

    @Autowired
    private final IBusinessHourService businessHourService;

    @Override
    public Booking getBookingById(Long id) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);

        if (bookingOptional.isPresent()) {
            return bookingOptional.get();
        }
        throw new BookingNotFoundException("There is no booking with id=" + id);
    }

    @Override
    public List<Booking> findByServiceId(Long serviceId) {
        return bookingRepository.findByServiceId(serviceId);
    }

    @Override
    public List<Booking> findByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Override
    public void save(Booking booking) {
        bookingRepository.save(booking);
    }

    @Override
    public Booking save(@NotNull BookingRequest bookingRequest) {
        User bookingUser = userService.getUserByEmailAndRole(bookingRequest.getEmail(), bookingRequest.getRole().name());
        Booking booking = Booking
                .builder()
                .user(bookingUser)
                .serviceId(bookingRequest.getServiceId())
                .date(bookingRequest.getDate())
                .startTime(bookingRequest.getStartTime())
                .endTime(bookingRequest.getEndTime())
                .build();
        save(booking);
        return booking;
    }

    public Map<Date, List<SlotRange>> getFreeSlots(Long businessId, Date date) {
        Map<Date, List<SlotRange>> freeSlots = new HashMap<>();
        BusinessHour businessHour = businessHourService.getBusinessHour(businessId);
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
            freeSlots.put(date, populateAllFreeSlots(start, end, duration));
        }
        return freeSlots;
    }

    private List<SlotRange> populateAllFreeSlots(LocalTime start, LocalTime end, long duration) {
        List<SlotRange> freeSlots = new ArrayList<>();
        List<SlotRange> bookedSlots = new ArrayList<>();
        bookedSlots.add(new SlotRange(LocalTime.of(9, 0), LocalTime.of(9, 30)));
        bookedSlots.add(new SlotRange(LocalTime.of(10, 30), LocalTime.of(11, 0)));
        bookedSlots.add(new SlotRange(LocalTime.of(11, 30), LocalTime.of(12, 0)));
        bookedSlots.add(new SlotRange(LocalTime.of(13, 0), LocalTime.of(15, 0)));
        bookedSlots.sort(Comparator.comparing(SlotRange::getStartTime));
        Map<LocalTime, Integer> mapSlots = new TreeMap<>();
        int availableTime = 0;
        end = end.minusMinutes(30);
        while (end.isAfter(start) || end.equals(start)) {
            LocalTime streamEnd = end;
            Optional<SlotRange> optional = bookedSlots.stream().filter(slot -> (slot.getStartTime().isBefore(streamEnd) && slot.getEndTime().isAfter(streamEnd)) || slot.getStartTime().equals(streamEnd)).findFirst();
            if (optional.isPresent()) {
                mapSlots.put(end, 0);
                availableTime = 0;
            } else {
                availableTime += 30;
                mapSlots.put(end, availableTime);
            }
            end = end.minusMinutes(30);
        }
        mapSlots.forEach((key, val) -> {
            if (val >= duration) {
                freeSlots.add(new SlotRange(key, key.plusMinutes(duration)));
            }
        });
        return freeSlots;
    }

    private String getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return new SimpleDateFormat("EEEE").format(cal.getTime());
    }
}
