package com.growbiz.backend.Booking.service;

import com.growbiz.backend.Booking.helper.BookingServiceHelper;
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


    @Autowired
    private final BookingServiceHelper helper;

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

    @Override
    public Map<Date, List<SlotRange>> getFreeSlotsForWeek(Long businessId, Date date) {
        Map<Date, List<SlotRange>> freeSlots = new HashMap<>();
        List<Date> dateListOfCurrentWeek = getCurrentWeekAllDates(date);
        BusinessHour businessHour = businessHourService.getBusinessHour(businessId);
        dateListOfCurrentWeek.forEach(day -> freeSlots.put(day, helper.getFreeSlots(date, businessHour)));
        return freeSlots;
    }

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

}
