package com.growbiz.backend.Categories.service.Sub;

import com.growbiz.backend.Categories.models.SubCategory;

import java.util.List;
import java.util.Optional;

public interface ISubCategoryService {
    SubCategory getSubCategoryByID(Long categoryID);

    List<SubCategory> fetchSubCategoryList();

    SubCategory addSubCategory(SubCategory newSubCategory);

    SubCategory updateSubCategory(SubCategory category, Long categoryID);

    void deleteSubCategory(Long categoryID);
}
