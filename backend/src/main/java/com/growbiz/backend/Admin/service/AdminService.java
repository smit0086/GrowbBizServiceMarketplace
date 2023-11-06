package com.growbiz.backend.Admin.service;

import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Categories.service.Sub.ISubCategoryService;
import com.growbiz.backend.Categories.service.Super.ICategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService implements IAdminService {

    @Autowired
    private final ICategoryService categoryService;

    @Autowired
    private final ISubCategoryService subCategoryService;

    @Override
    public Category addCategory(Category newCategory) {
        try {
            return categoryService.addCategory(newCategory, newCategory.getCategoryID());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Category updateCategory(Category updatedCategory) {
        try {
            return categoryService.updateCategory(updatedCategory, updatedCategory.getCategoryID());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean deleteCategory(Category category) {
        try {
            categoryService.deleteCategory(category.getCategoryID());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public SubCategory addSubCategory(SubCategory newSubCategory) {
        try {
            return subCategoryService.addCategory(newSubCategory, newSubCategory.getSubCategoryID());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SubCategory updateSubCategory(SubCategory updatedSubCategory) {
        try {
            return subCategoryService.updateSubCategory(updatedSubCategory, updatedSubCategory.getSubCategoryID());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean deleteSubCategory(SubCategory subCategory) {
        try {
            subCategoryService.deleteCategory(subCategory.getSubCategoryID());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
