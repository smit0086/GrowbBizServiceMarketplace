package com.growbiz.backend.Booking.repository;

import com.growbiz.backend.Booking.models.Booking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookingRepository extends CrudRepository<Booking, Long> {

    @Query("FROM Booking b WHERE b.user.id = :userId")
    List<Booking> findByUserId(@Param("userId") Long userId);

    List<Booking> findByServiceServiceId(Long serviceId);

    List<Booking> findByServiceBusinessBusinessId(Long businessId);
}
