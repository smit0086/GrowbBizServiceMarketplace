package com.growbiz.backend.Responses.BusinessHour;

import com.growbiz.backend.BusinessHour.model.BusinessHour;
import com.growbiz.backend.Responses.Basic.BasicResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class BusinessHourResponse extends BasicResponse {
    private BusinessHour businessHour;
}
