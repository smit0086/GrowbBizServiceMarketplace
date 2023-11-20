package com.growbiz.backend.BusinessHour.service;

import com.growbiz.backend.BusinessHour.model.BusinessHour;
import com.growbiz.backend.BusinessHour.repository.IBusinessHourRepository;
import com.growbiz.backend.RequestResponse.BusinessHour.BusinessHourRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Service
public class BusinessHourService implements IBusinessHourService {

    @Autowired
    IBusinessHourRepository businessHourRepository;

    @Override
    public void saveBusinessHours(BusinessHourRequest businessHourRequest) {
        BusinessHour.BusinessHourBuilder businessHour = BusinessHour.builder()
                .businessId(businessHourRequest.getBusinessId());
        setAllFieldsToNull(businessHour);
        if (businessHourRequest.getBusinessHours().containsKey(DayOfWeek.MONDAY)) {
            businessHour.monday_start(LocalTime.parse(businessHourRequest.getBusinessHours().get(DayOfWeek.MONDAY)[0]))
                    .monday_end(LocalTime.parse(businessHourRequest.getBusinessHours().get(DayOfWeek.MONDAY)[1]));
        }
        if (businessHourRequest.getBusinessHours().containsKey(DayOfWeek.TUESDAY)) {
            businessHour.tuesday_start(LocalTime.parse(businessHourRequest.getBusinessHours().get(DayOfWeek.TUESDAY)[0]))
                    .tuesday_end(LocalTime.parse(businessHourRequest.getBusinessHours().get(DayOfWeek.TUESDAY)[1]));
        }
        if (businessHourRequest.getBusinessHours().containsKey(DayOfWeek.WEDNESDAY)) {
            businessHour.wednesday_start(LocalTime.parse(businessHourRequest.getBusinessHours().get(DayOfWeek.WEDNESDAY)[0]))
                    .wednesday_end(LocalTime.parse(businessHourRequest.getBusinessHours().get(DayOfWeek.WEDNESDAY)[1]));
        }
        if (businessHourRequest.getBusinessHours().containsKey(DayOfWeek.THURSDAY)) {
            businessHour.thursday_start(LocalTime.parse(businessHourRequest.getBusinessHours().get(DayOfWeek.THURSDAY)[0]))
                    .thursday_end(LocalTime.parse(businessHourRequest.getBusinessHours().get(DayOfWeek.THURSDAY)[1]));
        }
        if (businessHourRequest.getBusinessHours().containsKey(DayOfWeek.FRIDAY)) {
            businessHour.friday_start(LocalTime.parse(businessHourRequest.getBusinessHours().get(DayOfWeek.FRIDAY)[0]))
                    .friday_end(LocalTime.parse(businessHourRequest.getBusinessHours().get(DayOfWeek.FRIDAY)[1]));
        }
        if (businessHourRequest.getBusinessHours().containsKey(DayOfWeek.SATURDAY)) {
            businessHour.saturday_start(LocalTime.parse(businessHourRequest.getBusinessHours().get(DayOfWeek.SATURDAY)[0]))
                    .saturday_end(LocalTime.parse(businessHourRequest.getBusinessHours().get(DayOfWeek.SATURDAY)[1]));
        }
        if (businessHourRequest.getBusinessHours().containsKey(DayOfWeek.SUNDAY)) {
            businessHour.sunday_start(LocalTime.parse(businessHourRequest.getBusinessHours().get(DayOfWeek.SUNDAY)[0]))
                    .sunday_end(LocalTime.parse(businessHourRequest.getBusinessHours().get(DayOfWeek.SUNDAY)[1]));
        }
        businessHourRepository.save(businessHour.build());
    }

    private void setAllFieldsToNull(BusinessHour.BusinessHourBuilder businessHour) {
        businessHour.monday_start(null).monday_end(null)
                .tuesday_start(null).tuesday_end(null)
                .wednesday_start(null).wednesday_end(null)
                .thursday_start(null).thursday_end(null)
                .friday_start(null).friday_end(null)
                .saturday_start(null).saturday_end(null)
                .sunday_start(null).sunday_end(null);
    }

    @Override
    public void init(Long businessId) {
        BusinessHour businessHour = BusinessHour.builder()
                .businessId(businessId)
                .monday_start(LocalTime.of(9, 0)).monday_end(LocalTime.of(17, 0))
                .tuesday_start(LocalTime.of(9, 0)).tuesday_end(LocalTime.of(17, 0))
                .wednesday_start(LocalTime.of(9, 0)).wednesday_end(LocalTime.of(17, 0))
                .thursday_start(LocalTime.of(9, 0)).thursday_end(LocalTime.of(17, 0))
                .friday_start(LocalTime.of(9, 0)).friday_end(LocalTime.of(17, 0))
                .build();
        businessHourRepository.save(businessHour);
    }

    @Override
    public BusinessHour getBusinessHour(Long businessId) {
        if (businessHourRepository.findById(businessId).isPresent()) {
            return businessHourRepository.findById(businessId).get();
        }
        return null;
    }
}
