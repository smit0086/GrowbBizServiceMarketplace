package com.growbiz.backend.Admin;

import com.growbiz.backend.Admin.service.AdminService;
import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Categories.models.SubCategoryRequest;
import com.growbiz.backend.Categories.service.Sub.SubCategoryService;
import com.growbiz.backend.Categories.service.Super.CategoryService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
    @InjectMocks
    private AdminService adminService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private SubCategoryService subCategoryService;

    @Mock
    Category mockCategory = Category
            .builder()
            .categoryID(1L)
            .name("Test Category")
            .tax("15")
            .build();

    @Mock
    SubCategory mockSubCategory = SubCategory
            .builder()
            .subCategoryID(1L)
            .name("Test Subcategory")
            .category(mockCategory)
            .build();

    @Mock
    SubCategoryRequest mockSubCategoryRequest = SubCategoryRequest
            .builder()
            .subCategoryID(1L)
            .name("Test SubCategory")
            .categoryID(mockCategory.getCategoryID())
            .isSubCategory(true)
            .build();

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addCategoryTest(){
        Category actualResponse;
        Category expectedResponse = mockCategory;

        when(categoryService.addCategory(mockCategory)).thenReturn(mockCategory);

        actualResponse = adminService.addCategory(mockCategory);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void addCategoryExceptionTest(){
        Category actualResponse;
        Category expectedResponse = null;

        when(categoryService.addCategory(mockCategory)).thenThrow(new RuntimeException("Test Exception"));

        actualResponse = adminService.addCategory(mockCategory);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void updateCategoryTest(){
        Category actualResponse;
        Category expectedResponse = mockCategory;

        when(categoryService.updateCategory(mockCategory)).thenReturn(mockCategory);

        actualResponse = adminService.updateCategory(mockCategory);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void updateCategoryExceptionTest(){
        Category actualResponse;
        Category expectedResponse = null;

        when(categoryService.updateCategory(mockCategory)).thenThrow(new RuntimeException("Test Exception"));

        actualResponse = adminService.updateCategory(mockCategory);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void deleteCategoryTest(){
        Boolean actualResponse;
        Boolean expectedResponse = true;

        doNothing().when(categoryService).deleteCategory(any());

        actualResponse = adminService.deleteCategory(mockCategory);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void deleteCategoryExceptionTest(){
        Boolean actualResponse;
        Boolean expectedResponse = false;

        doThrow(new RuntimeException("Test Exception")).when(categoryService).deleteCategory(any());

        actualResponse = adminService.deleteCategory(mockCategory);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void addSubCategoryTest(){
        SubCategory actualResponse;
        SubCategory expectedResponse = mockSubCategory;

        when(subCategoryService.addSubCategory(mockSubCategoryRequest)).thenReturn(mockSubCategory);

        actualResponse = adminService.addSubCategory(mockSubCategoryRequest);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void addSubCategoryExceptionTest(){
        SubCategory actualResponse;
        SubCategory expectedResponse = null;

        when(subCategoryService.addSubCategory(mockSubCategoryRequest)).thenThrow(new RuntimeException("Test Exception"));

        actualResponse = adminService.addSubCategory(mockSubCategoryRequest);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void updateSubCategoryTest(){
        SubCategory actualResponse;
        SubCategory expectedResponse = mockSubCategory;

        when(subCategoryService.updateSubCategory(mockSubCategoryRequest, mockSubCategoryRequest.getSubCategoryID())).thenReturn(mockSubCategory);

        actualResponse = adminService.updateSubCategory(mockSubCategoryRequest);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void updateSubCategoryExceptionTest(){
        SubCategory actualResponse;
        SubCategory expectedResponse = null;

        when(subCategoryService.updateSubCategory(mockSubCategoryRequest, mockSubCategoryRequest.getSubCategoryID())).thenThrow(new RuntimeException("Test Exception"));

        actualResponse = adminService.updateSubCategory(mockSubCategoryRequest);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void deleteSubCategoryTest(){
        Boolean actualResponse;
        Boolean expectedResponse = true;

        doNothing().when(subCategoryService).deleteSubCategory(any());

        actualResponse = adminService.deleteSubCategory(mockSubCategory);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void deleteSubCategoryExceptionTest(){
        Boolean actualResponse;
        Boolean expectedResponse = false;

        doThrow(new RuntimeException("Test Exception")).when(subCategoryService).deleteSubCategory(any());

        actualResponse = adminService.deleteSubCategory(mockSubCategory);
        assertEquals(actualResponse, expectedResponse);
    }
}
