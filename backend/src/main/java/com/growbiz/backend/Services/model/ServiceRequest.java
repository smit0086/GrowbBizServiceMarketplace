package com.growbiz.backend.Services.model;
import com.growbiz.backend.Responses.model.BasicRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ServiceRequest extends BasicRequest {
    private long serviceID;
    private String serviceName;
    private long businessID;
    private long subCategoryID;
}
