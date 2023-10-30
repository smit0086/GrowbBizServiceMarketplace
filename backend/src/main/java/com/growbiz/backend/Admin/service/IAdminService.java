package com.growbiz.backend.Admin.service;

import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;

public interface IAdminService {

    Category addCategory(Category category);

    Category updateCategory(Category category);

    Boolean deleteCategory(Category category);

    SubCategory addSubCategory(SubCategory subCategory);

    SubCategory updateSubCategory(SubCategory subCategory);

    Boolean deleteSubCategory(SubCategory subCategory);
}
