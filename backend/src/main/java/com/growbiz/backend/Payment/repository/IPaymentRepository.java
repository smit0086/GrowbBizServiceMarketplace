package com.growbiz.backend.Payment.repository;

import com.growbiz.backend.Payment.model.Payment;
import org.springframework.data.repository.CrudRepository;

public interface IPaymentRepository extends CrudRepository<Payment, Long> {
}
