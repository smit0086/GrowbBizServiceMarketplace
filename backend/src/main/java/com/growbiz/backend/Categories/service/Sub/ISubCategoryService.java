package com.growbiz.backend.Categories.service.Sub;

import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.RequestResponse.SubCategory.SubCategoryRequest;

import java.util.List;

public interface ISubCategoryService {
    SubCategory getSubCategoryByID(Long categoryID);

    List<SubCategory> fetchSubCategoryList();

    List<SubCategory> fetchSubCategoryListForCategoryID(Long categoryId);

    SubCategory addSubCategory(SubCategoryRequest newSubCategory);

    SubCategory updateSubCategory(SubCategoryRequest category, Long categoryID);

    void deleteSubCategory(Long categoryID);
}
