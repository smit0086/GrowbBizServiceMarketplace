package com.growbiz.backend.User.repository;

import com.growbiz.backend.User.models.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICustomerRepository extends CrudRepository<Customer, Long> {
    public List<Customer> findByEmail(String email);
}
