package com.growbiz.backend.Categories.service.Sub;

import com.growbiz.backend.Categories.models.SubCategory;

import java.util.List;
import java.util.Optional;

public interface ISubCategoryService {
    Optional<SubCategory> getSubCategoryByID(Long categoryID);

    List<SubCategory> fetchSubCategoryList();

    //needs checking
    void addCategory(SubCategory newSubCategory, Long newCategoryID);

    SubCategory updateSubCategory(SubCategory category, Long categoryID);

    void deleteCategory(Long categoryID);
}
