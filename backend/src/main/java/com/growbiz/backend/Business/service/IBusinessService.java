package com.growbiz.backend.Business.service;

import com.growbiz.backend.Business.model.Business;
import com.growbiz.backend.Business.model.BusinessRequest;

import java.util.List;

public interface IBusinessService {
    public List<Business> fetchAllBusinesses();

    public Business findByEmail(String email);

    public List<Business> fetchAllPendingBusinesses();

    Business save(BusinessRequest businessRequest);
}
