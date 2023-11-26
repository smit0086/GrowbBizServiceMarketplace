package com.growbiz.backend.RequestResponse.Services;

import com.growbiz.backend.RequestResponse.Basic.BasicResponse;
import com.growbiz.backend.Services.models.ServiceDTO;
import com.growbiz.backend.Services.models.Services;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ServiceResponse extends BasicResponse {
    private List<Services> services;
    private List<ServiceDTO> serviceDTOS;
    private String tax;
    private List<Double> avgRatings;
    private Long businessId;
    private Boolean isUpdated;
    private Boolean isDeleted;
}
