package com.growbiz.backend.Business.service;

import com.growbiz.backend.Business.model.BusinessHour;
import com.growbiz.backend.Business.model.BusinessHourRequest;

public interface IBusinessHourService {

    public void saveBusinessHours(BusinessHourRequest businessHourRequest);

    public void init(Long businessId);

    public BusinessHour getBusinessHour(Long businessId);

}
