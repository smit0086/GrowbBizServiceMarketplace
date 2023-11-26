package com.growbiz.backend.Payment.repository;

import com.growbiz.backend.Enums.PaymentStatus;
import com.growbiz.backend.Payment.model.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPaymentRepository extends CrudRepository<Payment, Long> {
    List<Payment> findByPaymentStatusEquals(PaymentStatus paymentStatus);

    List<Payment> findByServiceId(Long serviceId);

    List<Payment> findByUserEmail(String userEmail);
}
