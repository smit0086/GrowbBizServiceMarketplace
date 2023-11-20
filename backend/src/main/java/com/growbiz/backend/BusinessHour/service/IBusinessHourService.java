package com.growbiz.backend.BusinessHour.service;

import com.growbiz.backend.BusinessHour.model.BusinessHour;
import com.growbiz.backend.Responses.BusinessHour.BusinessHourRequest;

public interface IBusinessHourService {

    public void saveBusinessHours(BusinessHourRequest businessHourRequest);

    public void init(Long businessId);

    public BusinessHour getBusinessHour(Long businessId);

}
