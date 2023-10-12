package com.growbiz.backend.Categories.models;

import com.growbiz.backend.Responses.model.BasicResponse;
import com.growbiz.backend.User.models.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CategoryResponse extends BasicResponse {
    private long categoryID;
    private String categoryName;
}
