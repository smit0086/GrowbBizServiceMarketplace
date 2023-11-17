package com.growbiz.backend.Booking.service;

import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Booking.models.BookingRequest;
import com.growbiz.backend.Booking.models.BookingStatus;
import com.growbiz.backend.Booking.repository.IBookingRepository;
import com.growbiz.backend.Exception.exceptions.BookingNotFoundException;
import com.growbiz.backend.Services.service.IServicesService;
import com.growbiz.backend.User.models.User;
import com.growbiz.backend.User.service.IUserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService implements IBookingService {

    @Autowired
    private final IBookingRepository bookingRepository;

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
    public List<Booking> getAllBookingsByUserIdAndStatus(Long userId, String status) {
        return bookingRepository.findByUserIdAndStatus(userId, BookingStatus.valueOf(status));
    }

    @Override
    public List<Booking> findByBusinessId(Long businessId) {
        return bookingRepository.findByServiceBusinessBusinessId(businessId);
    }

    @Override
    public List<Booking> getAllBookingsByBusinessIdAndStatus(Long businessId, BookingStatus status) {
        return bookingRepository.findByServiceBusinessBusinessIdAndStatus(businessId, status);
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
                .status(bookingRequest.getStatus())
                .build();
        save(booking);
        return booking;
    }

}
