package com.growbiz.backend.Services.model;
import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Responses.model.BasicResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ServiceResponse extends BasicResponse {
    private List<Service> services;
    private Boolean isUpdated;
    private Boolean isDeleted;
}
