package com.growbiz.backend.RequestResponse.Services;

import com.growbiz.backend.RequestResponse.Basic.BasicRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ServiceRequest extends BasicRequest {
    private long serviceID;
    private String serviceName;
    private String description;
    private double price;
    private LocalTime timeRequired;
    private long businessID;
    private long subCategoryID;
    private String image;
}
