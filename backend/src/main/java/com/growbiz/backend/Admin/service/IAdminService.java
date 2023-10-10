package com.growbiz.backend.Admin.service;

import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.UserAuthentication.model.AuthenticationResponse;

public interface IAdminService {
    AuthenticationResponse addCategory(Category category);

    AuthenticationResponse updateCategory(Category category);

    AuthenticationResponse deleteCategory(Category category);

    AuthenticationResponse addSubCategory(SubCategory subCategory);

    AuthenticationResponse updateSubCategory(SubCategory subCategory);

    AuthenticationResponse deleteSubCategory(SubCategory subCategory);
}
