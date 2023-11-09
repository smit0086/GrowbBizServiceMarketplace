package com.growbiz.backend.Categories.models;

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
public class SubCategoryRequest extends BasicRequest {
    private long subCategoryID;
    private String subCategoryName;
    private long categoryID;
    private Boolean isSubCategory;
}
