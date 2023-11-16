package com.growbiz.backend.Payment.repository;

import com.growbiz.backend.Payment.model.Payment;
import com.growbiz.backend.Payment.model.PaymentStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IPaymentRepository extends CrudRepository<Payment, Long> {
    List<Payment> findByPaymentStatusEquals(PaymentStatus paymentStatus);

    List<Payment> findByServiceId(Long serviceId);
}
