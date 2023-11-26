package com.growbiz.backend.RequestResponse.BusinessHour;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessHourRequest {
    Long businessId;
    Map<DayOfWeek, String[]> businessHours;
}
