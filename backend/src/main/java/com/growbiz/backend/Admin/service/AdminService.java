package com.growbiz.backend.Admin.service;

import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Categories.models.SubCategoryRequest;
import com.growbiz.backend.Categories.service.Sub.ISubCategoryService;
import com.growbiz.backend.Categories.service.Super.ICategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            return categoryService.addCategory(newCategory);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Category updateCategory(Category updatedCategory) {
        try {
            return categoryService.updateCategory(updatedCategory);
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
    public SubCategory addSubCategory(SubCategoryRequest newSubCategory) {
        try {
            return subCategoryService.addSubCategory(newSubCategory);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SubCategory updateSubCategory(SubCategoryRequest updatedSubCategory) {
        try {
            return subCategoryService.updateSubCategory(updatedSubCategory, updatedSubCategory.getCategoryID());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean deleteSubCategory(SubCategory subCategory) {
        try {
            subCategoryService.deleteSubCategory(subCategory.getSubCategoryID());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
