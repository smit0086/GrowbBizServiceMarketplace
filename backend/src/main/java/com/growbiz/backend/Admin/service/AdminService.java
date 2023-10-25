package com.growbiz.backend.Admin.service;

import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Categories.service.Sub.ISubCategoryService;
import com.growbiz.backend.Categories.service.Super.ICategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService implements IAdminService{

    @Autowired
    private final ICategoryService categoryService;

    @Autowired
    private final ISubCategoryService subCategoryService;

    @Override
    public List<Category> fetchAllCategories() {
        return StreamSupport.stream(categoryService.fetchCategoryList().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Category fetchCategoryByID(long categoryID) {
        return categoryService.getCategoryByID(categoryID);
    }

    @Override
    public Category addCategory(Category newCategory) {
        try {
            Category category = categoryService.addCategory(newCategory, newCategory.getCategoryID());
            return category;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Category updateCategory(Category updatedCategory) {
        try {
            Category category = categoryService.updateCategory(updatedCategory, updatedCategory.getCategoryID());
            return category;
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
    public List<SubCategory> fetchAllSubCategories() {
        return StreamSupport.stream(subCategoryService.fetchSubCategoryList().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public SubCategory fetchSubCategoryByID(long subCategoryID) {
        return subCategoryService.getSubCategoryByID(subCategoryID);
    }

    @Override
    public SubCategory addSubCategory(SubCategory newSubCategory) {
        try {
            SubCategory subCategory = subCategoryService.addCategory(newSubCategory, newSubCategory.getSubCategoryID());
            return subCategory;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SubCategory updateSubCategory(SubCategory updatedSubCategory) {
        try {
            SubCategory subCategory = subCategoryService.updateSubCategory(updatedSubCategory, updatedSubCategory.getSubCategoryID());
            return subCategory;
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
