package com.growbiz.backend.Admin.controller;

import com.growbiz.backend.Exception.exceptions.CategoryNotFoundException;
import com.growbiz.backend.Exception.exceptions.CategoryAlreadyExistsException;
import com.growbiz.backend.Exception.exceptions.SubCategoryNotFoundException;
import com.growbiz.backend.Exception.exceptions.SubCategoryAlreadyExistsException;
import com.growbiz.backend.Admin.service.IAdminService;
import com.growbiz.backend.Categories.helper.CategoriesControllerHelper;
import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.CategoryRequest;
import com.growbiz.backend.Categories.models.CategoryResponse;
import com.growbiz.backend.Categories.models.SubCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private CategoriesControllerHelper helper;

    @GetMapping(path = "/allCategories")
    public ResponseEntity<CategoryResponse> getAllCategories() {
        return helper.createCategoryResponse(adminService.fetchAllCategories());
    }

    @GetMapping(path = "/get")
    public ResponseEntity<CategoryResponse> getCategory(@RequestBody CategoryRequest categoryRequest) {
        return helper.createCategoryResponse(List.of(adminService.fetchCategoryByID(categoryRequest.getCategoryID())));
    }

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
    public ResponseEntity<CategoryResponse> updateCategory(@RequestBody Category newCategory) throws Exception {
        Category category = adminService.updateCategory(newCategory);

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

    @GetMapping(path = "/allSubCategories")
    public ResponseEntity<CategoryResponse> getAllSubCategories() {
        return helper.createSubCategoryResponse(adminService.fetchAllSubCategories());
    }

    @GetMapping(path = "/getSub")
    public ResponseEntity<CategoryResponse> getSubCategory(@RequestBody CategoryRequest categoryRequest) {
        return helper.createSubCategoryResponse(List.of(adminService.fetchSubCategoryByID(categoryRequest.getCategoryID())));
    }

    @PostMapping(path = "/addSubCategory")
    public ResponseEntity<CategoryResponse> addSubCategory(@RequestBody SubCategory newCategory) throws Exception {
        SubCategory subCategory = adminService.addSubCategory(newCategory);

        if (subCategory != null) {
            return helper.createSubCategoryResponse(List.of(subCategory));
        } else {
            throw new SubCategoryAlreadyExistsException("The requested new subcategory to add, already exists!");
        }
    }

    @PostMapping(path = "/updateSubCategory")
    public ResponseEntity<CategoryResponse> updateSubCategory(@RequestBody SubCategory newCategory) throws Exception {
        SubCategory subCategory = adminService.updateSubCategory(newCategory);

        if (subCategory != null) {
            return helper.createSubCategoryResponse(List.of(subCategory));
        } else {
            throw new SubCategoryNotFoundException("The specified subcategory for update in not found");
        }
    }

    @PostMapping(path = "/deleteSubCategory")
    public ResponseEntity<CategoryResponse> deleteSubCategory(@RequestBody SubCategory newSubCategory) throws Exception {
        Boolean isDeleted = adminService.deleteSubCategory(newSubCategory);

        if (isDeleted) {
            return helper.deleteCategoryResponse(true, true);
        } else {
            throw new SubCategoryNotFoundException("The specified subcategory for deletion in not found");
        }
    }

}
