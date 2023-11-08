package com.growbiz.backend.Booking.service;

import com.growbiz.backend.Booking.helper.BookingServiceHelper;
import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Booking.models.BookingRequest;
import com.growbiz.backend.Booking.models.SlotRange;
import com.growbiz.backend.Booking.repository.IBookingRepository;
import com.growbiz.backend.Business.model.BusinessHour;
import com.growbiz.backend.Business.service.IBusinessHourService;
import com.growbiz.backend.Exception.exceptions.BookingNotFoundException;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.Services.service.IServicesService;
import com.growbiz.backend.User.models.User;
import com.growbiz.backend.User.service.IUserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService implements IBookingService {

    @Autowired
    private final IBookingRepository bookingRepository;

    @Autowired
    private final BookingServiceHelper helper;

    @Autowired
    private final IBusinessHourService businessHourService;

    @Autowired
    private final IUserService userService;

    @Autowired
    private final IServicesService servicesService;

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
        return bookingRepository.findByServiceServiceId(serviceId);
    }

    @Override
    public List<Booking> findByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Override
    public List<Booking> findByBusinessId(Long businessId) {
        return bookingRepository.findByServiceBusinessBusinessId(businessId);
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
                .service(servicesService.getServiceById(bookingRequest.getServiceId()))
                .date(bookingRequest.getDate())
                .startTime(bookingRequest.getStartTime())
                .endTime(bookingRequest.getEndTime())
                .amount(bookingRequest.getAmount())
                .note(bookingRequest.getNote())
                .build();
        save(booking);
        return booking;
    }

    /**
     * The method does the following things:
     * 1. get all the current Week dates.
     * 2. fetches the business hour of the given businessId.
     * 3. fetch all the services of the given businessId.
     * 4. fetch all the booking list of the services fetched in step3.
     * 5. get free slots for all the days in the current week using getFreeSlots method.
     * 6. sort the final list using the date and return it.
     *
     * @param businessId - businessId
     * @param date       - date
     * @param serviceId  - serviceId
     * @return - map of Date and All the free slots
     * @author - an370985@dal.ca
     */
    @Override
    public Map<Date, List<SlotRange>> getFreeSlotsForWeek(Long businessId, Date date, Long serviceId) {
        Map<Date, List<SlotRange>> freeSlots = new HashMap<>();
        List<Date> dateListOfCurrentWeek = helper.getCurrentWeekAllDates(date);
        BusinessHour businessHour = businessHourService.getBusinessHour(businessId);
        List<Services> servicesList = servicesService.getServiceByBusinessId(servicesService.getServiceById(serviceId).getBusiness().getBusinessId());
        List<Booking> bookingList = new ArrayList<>();
        servicesList.forEach(services -> bookingList.addAll(findByServiceId(services.getServiceId())));
        dateListOfCurrentWeek.forEach(day -> freeSlots.put(day, helper.getFreeSlots(date, businessHour, serviceId, bookingList)));
        return freeSlots.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
