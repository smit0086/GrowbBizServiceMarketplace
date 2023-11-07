package com.growbiz.backend.Business.model;

import com.growbiz.backend.Responses.model.BasicResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class BusinessHourResponse extends BasicResponse {
    private BusinessHour businessHour;
}
