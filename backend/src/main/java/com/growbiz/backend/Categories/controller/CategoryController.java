package com.growbiz.backend.Categories.controller;


import com.growbiz.backend.Categories.helper.CategoryControllerHelper;
import com.growbiz.backend.Categories.models.CategoryResponse;
import com.growbiz.backend.Categories.service.Sub.ISubCategoryService;
import com.growbiz.backend.Categories.service.Super.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private final ICategoryService categoryService;

    @Autowired
    private final ISubCategoryService subCategoryService;

    @Autowired
    private final CategoryControllerHelper helper;

    @GetMapping(path = "/allCategories")
    public ResponseEntity<CategoryResponse> getAllCategories() {
        return helper.createCategoryResponse(categoryService.fetchCategoryList());
    }

    @GetMapping(path = "/getCategory")
    public ResponseEntity<CategoryResponse> getCategory(@RequestParam Long categoryId) {
        return helper.createCategoryResponse(List.of(categoryService.getCategoryByID(categoryId)));
    }

    @GetMapping(path = "/allSubCategories")
    public ResponseEntity<CategoryResponse> getAllSubCategories() {
        return helper.createSubCategoryResponse(subCategoryService.fetchSubCategoryList());
    }

    @GetMapping(path = "/getSubCategory")
    public ResponseEntity<CategoryResponse> getSubCategory(@RequestParam Long subCategoryId) {
        return helper.createSubCategoryResponse(List.of(subCategoryService.getSubCategoryByID(subCategoryId)));
    }
}
