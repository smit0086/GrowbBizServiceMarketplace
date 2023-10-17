package com.growbiz.backend.Business.service;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.Map;

public interface IBusinessHourService {

    public void saveBusinessHours(Long businessId, Map<DayOfWeek, Time[]> businessHours);

    public void init(Long businessId);
}
