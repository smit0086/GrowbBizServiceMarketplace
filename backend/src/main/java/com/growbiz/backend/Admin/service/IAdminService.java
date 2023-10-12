package com.growbiz.backend.Admin.service;

import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;

import java.util.List;
import java.util.Optional;

public interface IAdminService {
    List<Category> fetchAllCategories();

    Category fetchCategoryByID(long categoryID);

    Category addCategory(Category category);

    Category updateCategory(Category category);

    Boolean deleteCategory(Category category);

    List<SubCategory> fetchAllSubCategories();

    SubCategory fetchSubCategoryByID(long subCategoryID);

    SubCategory addSubCategory(SubCategory subCategory);

    SubCategory updateSubCategory(SubCategory subCategory);

    Boolean deleteSubCategory(SubCategory subCategory);
}
