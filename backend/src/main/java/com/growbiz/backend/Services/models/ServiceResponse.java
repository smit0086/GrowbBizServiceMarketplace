package com.growbiz.backend.Services.models;

import com.growbiz.backend.Responses.Basic.BasicResponse;
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
    private Long businessId;
    private Boolean isUpdated;
    private Boolean isDeleted;
}
