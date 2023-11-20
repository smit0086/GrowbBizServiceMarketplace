package com.growbiz.backend.Admin.service;

import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.RequestResponse.SubCategory.SubCategoryRequest;

public interface IAdminService {

    Category addCategory(Category category);

    Category updateCategory(Category category);

    Boolean deleteCategory(Category category);

    SubCategory addSubCategory(SubCategoryRequest subCategory);

    SubCategory updateSubCategory(SubCategoryRequest subCategory);

    Boolean deleteSubCategory(SubCategory subCategory);
}
