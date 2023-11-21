package com.growbiz.backend.Categories;

import com.growbiz.backend.Categories.controller.CategoryController;
import com.growbiz.backend.Categories.helper.CategoryControllerHelper;
import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Categories.service.Sub.ISubCategoryService;
import com.growbiz.backend.Categories.service.Super.ICategoryService;
import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.RequestResponse.Category.CategoryResponse;
import com.growbiz.backend.TestConstants.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
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
    Category mockCategory;

    @Mock
    SubCategory mockSubCategory;

    @BeforeEach
    public void init() {
        mockCategory = Category
            .builder()
            .categoryID(1L)
            .name(TestConstants.TEST_CATEGORY_NAME)
            .tax(TestConstants.TEST_CATEGORY_TAX)
            .build();
        mockSubCategory = SubCategory
                .builder()
                .subCategoryID(1L)
                .name(TestConstants.TEST_SUBCATEGORY_NAME)
                .category(mockCategory)
                .build();
    }

    @Test
    public void getCategoryTest() {
        when(categoryService.getCategoryByID(1L)).thenReturn(mockCategory);
        ResponseEntity<CategoryResponse> expectedResponse = ResponseEntity.ok(CategoryResponse.builder().categories(List.of(mockCategory)).role(Role.ADMIN).subject("testEmail@dal.ca").build());
        when(categoryControllerHelper.createCategoryResponse(List.of(mockCategory))).thenReturn(expectedResponse);
        ResponseEntity<CategoryResponse> results = categoryController.getCategory(1L);
        assertEquals(expectedResponse, results);
    }

    @Test
    public void getSubCategoryTest() {
        when(subCategoryService.getSubCategoryByID(1L)).thenReturn(mockSubCategory);
        ResponseEntity<CategoryResponse> expectedResponse = ResponseEntity.ok(CategoryResponse.builder().subCategories(List.of(mockSubCategory)).role(Role.ADMIN).subject("testEmail@dal.ca").build());
        when(categoryControllerHelper.createSubCategoryResponse(List.of(mockSubCategory))).thenReturn(expectedResponse);
        ResponseEntity<CategoryResponse> results = categoryController.getSubCategory(1L);
        assertEquals(expectedResponse, results);
    }

    @Test
    public void getAllSubCategoriesForCategoryIdTest() {
        when(categoryService.getCategoryByID(1L)).thenReturn(mockCategory);
        when(subCategoryService.fetchSubCategoryListForCategoryID(1L)).thenReturn(List.of(mockSubCategory));
        ResponseEntity<CategoryResponse> expectedResponse = ResponseEntity.ok(CategoryResponse
                .builder()
                .categories(List.of(mockCategory))
                .subCategories(List.of(mockSubCategory))
                .role(Role.ADMIN)
                .subject(TestConstants.TEST_EMAIL)
                .build());
        when(categoryControllerHelper.createCategoryResponse(List.of(mockCategory), List.of(mockSubCategory))).thenReturn(expectedResponse);
        ResponseEntity<CategoryResponse> results = categoryController.getAllSubCategoriesForCategoryId(1L);
        assertEquals(expectedResponse, results);
    }

    @Test
    public void getAllCategoriesTest() {
        when(categoryService.fetchCategoryList()).thenReturn(List.of(mockCategory));
        ResponseEntity<CategoryResponse> expectedResponse = ResponseEntity.ok(CategoryResponse
                .builder()
                .categories(List.of(mockCategory))
                .role(Role.ADMIN)
                .subject(TestConstants.TEST_EMAIL)
                .build());
        when(categoryControllerHelper.createCategoryResponse(List.of(mockCategory))).thenReturn(expectedResponse);
        ResponseEntity<CategoryResponse> results = categoryController.getAllCategories();
        assertEquals(expectedResponse, results);
    }

    @Test
    public void getAllSubCategoriesTest() {
        when(subCategoryService.fetchSubCategoryList()).thenReturn(List.of(mockSubCategory));
        ResponseEntity<CategoryResponse> expectedResponse = ResponseEntity.ok(CategoryResponse
                .builder()
                .subCategories(List.of(mockSubCategory))
                .role(Role.ADMIN)
                .subject(TestConstants.TEST_EMAIL)
                .build());
        when(categoryControllerHelper.createSubCategoryResponse(List.of(mockSubCategory))).thenReturn(expectedResponse);
        ResponseEntity<CategoryResponse> results = categoryController.getAllSubCategories();
        assertEquals(expectedResponse, results);
    }
}
