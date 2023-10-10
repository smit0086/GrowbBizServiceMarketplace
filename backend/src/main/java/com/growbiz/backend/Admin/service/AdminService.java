package com.growbiz.backend.Admin.service;

import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Categories.service.Sub.ISubCategoryService;
import com.growbiz.backend.Categories.service.Super.ICategoryService;
import com.growbiz.backend.UserAuthentication.model.AuthenticationResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService implements IAdminService{

    @Autowired
    private final ICategoryService categoryService;

    @Autowired
    private final ISubCategoryService subCategoryService;

    @Override
    public AuthenticationResponse addCategory(Category category) {
        try {
            categoryService.addCategory(category, category.getId());
            return AuthenticationResponse.builder()
                    .token("Pass")
                    .subject("Category ID: " + category.getId().toString() + "Added!")
                    .build();
        } catch (Exception e) {
            return AuthenticationResponse.builder()
                    .token("Fail")
                    .subject("Category ID: " + category.getId().toString() + "Failed!")
                    .build();
        }
    }

    @Override
    public AuthenticationResponse updateCategory(Category category) {
        try {
            if (categoryService.updateCategory(category, category.getId()) != null) {
                return AuthenticationResponse.builder()
                        .token("Pass")
                        .subject("Category ID: " + category.getId().toString() + "Added!")
                        .build();
            } else {
                return AuthenticationResponse.builder()
                        .token("Fail")
                        .subject("Category ID: " + category.getId().toString() + "Failed!")
                        .build();
            }
        } catch (Exception e) {
            return AuthenticationResponse.builder()
                    .token("Fail")
                    .subject("Category ID: " + category.getId().toString() + "Failed!")
                    .build();
        }
    }

    @Override
    public AuthenticationResponse deleteCategory(Category category) {
        try {
            categoryService.deleteCategory(category.getId());
            return AuthenticationResponse.builder()
                    .token("Pass")
                    .subject("Category ID: " + category.getId().toString() + "Added!")
                    .build();
        } catch (Exception e) {
            return AuthenticationResponse.builder()
                    .token("Fail")
                    .subject("Category ID: " + category.getId().toString() + "Failed!")
                    .build();
        }
    }

    @Override
    public AuthenticationResponse addSubCategory(SubCategory subCategory) {
        try {
            subCategoryService.addCategory(subCategory, subCategory.getId());
            return AuthenticationResponse.builder()
                    .token("Pass")
                    .subject("Category ID: " + subCategory.getId().toString() + "Added!")
                    .build();
        } catch (Exception e) {
            return AuthenticationResponse.builder()
                    .token("Fail")
                    .subject("Category ID: " + subCategory.getId().toString() + "Failed!")
                    .build();
        }
    }

    @Override
    public AuthenticationResponse updateSubCategory(SubCategory subCategory) {
        try {
            if (subCategoryService.updateSubCategory(subCategory, subCategory.getId()) != null) {
                return AuthenticationResponse.builder()
                        .token("Pass")
                        .subject("Category ID: " + subCategory.getId().toString() + "Added!")
                        .build();
            } else {
                return AuthenticationResponse.builder()
                        .token("Fail")
                        .subject("Category ID: " + subCategory.getId().toString() + "Failed!")
                        .build();
            }
        } catch (Exception e) {
            return AuthenticationResponse.builder()
                    .token("Fail")
                    .subject("Category ID: " + subCategory.getId().toString() + "Failed!")
                    .build();
        }
    }

    @Override
    public AuthenticationResponse deleteSubCategory(SubCategory subCategory) {
        try {
            subCategoryService.deleteCategory(subCategory.getId());
            return AuthenticationResponse.builder()
                    .token("Pass")
                    .subject("Category ID: " + subCategory.getId().toString() + "Added!")
                    .build();
        } catch (Exception e) {
            return AuthenticationResponse.builder()
                    .token("Fail")
                    .subject("Category ID: " + subCategory.getId().toString() + "Failed!")
                    .build();
        }
    }

}
