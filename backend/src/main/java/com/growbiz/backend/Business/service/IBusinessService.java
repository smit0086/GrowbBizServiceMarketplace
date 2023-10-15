package com.growbiz.backend.Business.service;

import com.growbiz.backend.Business.model.Business;
import com.growbiz.backend.Business.model.BusinessRequest;

import java.util.List;

public interface IBusinessService {
    List<Business> fetchBusinesses(String role);

    public List<Business> fetchAllBusinesses();

    public Business findByEmail(String email);

    public Business findById(Long businessId);

    public Business save(BusinessRequest businessRequest);

    public void save(Business business);

    public Business updateBusiness(BusinessRequest businessRequest, Long businessId);
}
