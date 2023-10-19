package com.growbiz.backend.Booking.service;

import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Booking.models.BookingRequest;
import com.growbiz.backend.Booking.repository.IBookingRepository;
import com.growbiz.backend.Exception.exceptions.BookingNotFoundException;
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

    @Override
    public Booking getBookingById(Long id) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);

        if (bookingOptional.isPresent()) {
            return bookingOptional.get();
        }
        throw new BookingNotFoundException("There is no booking with id=" + id);
    }

    @Override
    public List<Booking> getAllBookingsByUserId(Long userId) {
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
                .startTime(bookingRequest.getStartTime())
                .endTime(bookingRequest.getEndTime())
                .build();
        bookingRepository.save(booking);
        return booking;
    }
}
