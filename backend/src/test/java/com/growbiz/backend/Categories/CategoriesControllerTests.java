package com.growbiz.backend.Categories;

import com.growbiz.backend.Categories.controller.CategoryController;
import com.growbiz.backend.Categories.helper.CategoryControllerHelper;
import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.CategoryResponse;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Categories.service.Sub.ISubCategoryService;
import com.growbiz.backend.Categories.service.Super.ICategoryService;
import com.growbiz.backend.User.models.Role;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CategoriesControllerTests {
    @Mock
    private ICategoryService categoryService;
    @Mock
    private ISubCategoryService subCategoryService;
    @Mock
    private CategoryControllerHelper categoryControllerHelper;
    @InjectMocks
    private CategoryController categoryController;

    @Mock
    Category mockCategory = Category
            .builder()
            .categoryID(1L)
            .name("Category 1")
            .tax("15")
            .build();

    @Mock
    SubCategory mockSubCategory = SubCategory
            .builder()
            .subCategoryID(1L)
            .name("Sub Category 1")
            .category(mockCategory)
            .build();

    @Before
    public void init () {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getCategorySuccessTest(){
        when(categoryService.getCategoryByID(1L)).thenReturn(mockCategory);
        ResponseEntity<CategoryResponse> expectedResponse = ResponseEntity.ok(CategoryResponse.builder().categories(List.of(mockCategory)).role(Role.ADMIN).subject("testEmail@dal.ca").build());
        when(categoryControllerHelper.createCategoryResponse(List.of(mockCategory))).thenReturn(expectedResponse);
        ResponseEntity<CategoryResponse> results = categoryController.getCategory(1L);
        assertEquals(expectedResponse, results);
    }
    @Test
    public void getSubCategorySuccessTest(){
        when(subCategoryService.getSubCategoryByID(1L)).thenReturn(mockSubCategory);
        ResponseEntity<CategoryResponse> expectedResponse = ResponseEntity.ok(CategoryResponse.builder().subCategories(List.of(mockSubCategory)).role(Role.ADMIN).subject("testEmail@dal.ca").build());
        when(categoryControllerHelper.createSubCategoryResponse(List.of(mockSubCategory))).thenReturn(expectedResponse);
        ResponseEntity<CategoryResponse> results = categoryController.getSubCategory(1L);
        assertEquals(expectedResponse, results);
    }

    @Test
    public void getAllSubCategoriesForCategoryIdSuccessTest() {
        when(categoryService.getCategoryByID(1L)).thenReturn(mockCategory);
        when(subCategoryService.fetchSubCategoryListForCategoryID(1L)).thenReturn(List.of(mockSubCategory));
        ResponseEntity<CategoryResponse> expectedResponse = ResponseEntity.ok(CategoryResponse
                .builder()
                .categories(List.of(mockCategory))
                .subCategories(List.of(mockSubCategory))
                .role(Role.ADMIN)
                .subject("testEmail@dal.ca")
                .build());
        when(categoryControllerHelper.createCategoryResponse(List.of(mockCategory), List.of(mockSubCategory))).thenReturn(expectedResponse);
        ResponseEntity<CategoryResponse> results = categoryController.getAllSubCategoriesForCategoryId(1L);
        assertEquals(expectedResponse, results);
    }

}
