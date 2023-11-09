package com.growbiz.backend.Categories.service.Sub;

import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Categories.models.SubCategoryRequest;

import java.util.List;
import java.util.Optional;

public interface ISubCategoryService {
    SubCategory getSubCategoryByID(Long categoryID);

    List<SubCategory> fetchSubCategoryList();

    List<SubCategory> fetchSubCategoryListForCategoryID(Long categoryId);

    SubCategory addSubCategory(SubCategoryRequest newSubCategory);

    SubCategory updateSubCategory(SubCategoryRequest category, Long categoryID);

    void deleteSubCategory(Long categoryID);
}
