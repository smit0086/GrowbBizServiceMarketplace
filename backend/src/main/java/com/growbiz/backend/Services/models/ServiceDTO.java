package com.growbiz.backend.Services.models;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;

@Data
@SuperBuilder
public class ServiceDTO {
    private Long serviceId;

    private String serviceName;

    private String description;

    private Double price;

    private LocalTime timeRequired;

    private Long businessId;

    private Long subCategoryId;

    private String imageURL;
}
