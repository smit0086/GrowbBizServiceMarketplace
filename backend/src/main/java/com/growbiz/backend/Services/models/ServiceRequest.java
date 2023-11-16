package com.growbiz.backend.Services.models;

import com.growbiz.backend.Responses.model.BasicRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
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
