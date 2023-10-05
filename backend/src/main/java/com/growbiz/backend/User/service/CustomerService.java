package com.growbiz.backend.User.service;

import com.growbiz.backend.User.models.Customer;
import com.growbiz.backend.User.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    ICustomerRepository iCustomerRepository;

    public Customer getCustomerByEmail(String email) {
        List<Customer> list = iCustomerRepository.findByEmail(email);
        if (list.isEmpty()) {
            throw new UsernameNotFoundException("User is not there");
        }
        return list.get(0);
    }

    public void saveCustomer(Customer customer) {
        iCustomerRepository.save(customer);
    }
}
