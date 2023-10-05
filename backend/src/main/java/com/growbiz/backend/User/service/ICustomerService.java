package com.growbiz.backend.User.service;

import com.growbiz.backend.User.models.Customer;
import org.springframework.stereotype.Service;

@Service
public interface ICustomerService {
    public Customer getCustomerByEmail(String email);

    public void saveCustomer(Customer customer);

}
