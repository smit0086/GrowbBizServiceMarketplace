package com.growbiz.backend.Categories.service.Sub;

import com.growbiz.backend.Categories.models.SubCategory;

import java.util.List;
import java.util.Optional;

public interface ISubCategoryService {
    SubCategory getSubCategoryByID(Long categoryID);

    List<SubCategory> fetchSubCategoryList();

    //needs checking
    SubCategory addCategory(SubCategory newSubCategory, Long newCategoryID);

    SubCategory updateSubCategory(SubCategory category, Long categoryID);

    void deleteCategory(Long categoryID);
}
