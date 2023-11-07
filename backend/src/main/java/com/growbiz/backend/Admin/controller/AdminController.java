package com.growbiz.backend.Admin.controller;

import com.growbiz.backend.Admin.helper.AdminControllerHelper;
import com.growbiz.backend.Admin.service.IAdminService;
import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.CategoryResponse;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Exception.exceptions.CategoryAlreadyExistsException;
import com.growbiz.backend.Exception.exceptions.CategoryNotFoundException;
import com.growbiz.backend.Exception.exceptions.SubCategoryAlreadyExistsException;
import com.growbiz.backend.Exception.exceptions.SubCategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private AdminControllerHelper helper;

    @PostMapping(path = "/addCategory")
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody Category newCategory) throws Exception {
        Category category = adminService.addCategory(newCategory);

        if (category != null) {
            return helper.createCategoryResponse(List.of(category));
        } else {
            throw new CategoryAlreadyExistsException("The requested new category to add, already exists!");
        }
    }

    @PostMapping(path = "/updateCategory")
    public ResponseEntity<CategoryResponse> updateCategory(@RequestBody Category updatedCategory) throws Exception {
        Category category = adminService.updateCategory(updatedCategory);

        if (category != null) {
            return helper.createCategoryResponse(List.of(category));
        } else {
            throw new CategoryNotFoundException("The specified category for update in not found");
        }
    }

    @PostMapping(path = "/deleteCategory")
    public ResponseEntity<CategoryResponse> deleteCategory(@RequestBody Category oldCategory) throws Exception {
        Boolean isDeleted = adminService.deleteCategory(oldCategory);

        if (isDeleted) {
            return helper.deleteCategoryResponse(false, true);
        } else {
            throw new CategoryNotFoundException("The specified category for deletion in not found");
        }
    }

    @PostMapping(path = "/addSubCategory")
    public ResponseEntity<CategoryResponse> addSubCategory(@RequestBody SubCategory newSubCategory) throws Exception {
        SubCategory subCategory = adminService.addSubCategory(newSubCategory);

        if (subCategory != null) {
            return helper.createSubCategoryResponse(List.of(subCategory));
        } else {
            throw new SubCategoryAlreadyExistsException("The requested new subcategory to add, already exists!");
        }
    }

    @PostMapping(path = "/updateSubCategory")
    public ResponseEntity<CategoryResponse> updateSubCategory(@RequestBody SubCategory updatedSubCategory) throws Exception {
        SubCategory subCategory = adminService.updateSubCategory(updatedSubCategory);

        if (subCategory != null) {
            return helper.createSubCategoryResponse(List.of(subCategory));
        } else {
            throw new SubCategoryNotFoundException("The specified subcategory for update in not found");
        }
    }

    @PostMapping(path = "/deleteSubCategory")
    public ResponseEntity<CategoryResponse> deleteSubCategory(@RequestBody SubCategory oldSubCategory) throws Exception {
        Boolean isDeleted = adminService.deleteSubCategory(oldSubCategory);

        if (isDeleted) {
            return helper.deleteCategoryResponse(true, true);
        } else {
            throw new SubCategoryNotFoundException("The specified subcategory for deletion in not found");
        }
    }

}
