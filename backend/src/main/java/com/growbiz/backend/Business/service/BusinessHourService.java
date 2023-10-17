package com.growbiz.backend.Business.service;

import com.growbiz.backend.Business.model.BusinessHour;
import com.growbiz.backend.Business.repository.IBusinessHourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.Map;

@Service
public class BusinessHourService implements IBusinessHourService {

    @Autowired
    IBusinessHourRepository businessHourRepository;

    @Override
    public void saveBusinessHours(Long businessId, Map<DayOfWeek, Time[]> businessHours) {
        BusinessHour businessHour = BusinessHour.builder()
                .businessId(businessId)
                .monday_start(businessHours.get(DayOfWeek.MONDAY)[0])
                .monday_end(businessHours.get(DayOfWeek.MONDAY)[1])
                .build();
        businessHourRepository.save(businessHour);
    }

    @Override
    public void init(Long businessId) {
        BusinessHour businessHour = BusinessHour.builder()
                .businessId(businessId)
                .monday_start(new Time(540))
                .monday_end(new Time(600))
                .build();
        businessHourRepository.save(businessHour);
    }
}
