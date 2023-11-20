package com.growbiz.backend.Categories.models;

import com.growbiz.backend.Responses.Basic.BasicRequest;
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
public class CategoryRequest extends BasicRequest {
    private long categoryID;
    private String categoryName;
    private Boolean isSubCategory;
}
