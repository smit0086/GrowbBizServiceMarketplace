package com.growbiz.backend.Booking.repository;

import com.growbiz.backend.Booking.models.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookingRepository extends CrudRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);
}
