package com.growbiz.backend.RequestResponse.Category;

import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.RequestResponse.Basic.BasicResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CategoryResponse extends BasicResponse {
    private List<Category> categories;
    private List<SubCategory> subCategories;
    private Boolean isSubCategory;
    private Boolean isDeleted;
}
