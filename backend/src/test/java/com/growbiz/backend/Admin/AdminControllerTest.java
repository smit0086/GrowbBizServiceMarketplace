package com.growbiz.backend.Admin;

import com.growbiz.backend.Admin.controller.AdminController;
import com.growbiz.backend.Admin.helper.AdminControllerHelper;
import com.growbiz.backend.Admin.service.AdminService;
import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.CategoryResponse;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Categories.models.SubCategoryRequest;
import com.growbiz.backend.Categories.repository.ICategoryRepository;
import com.growbiz.backend.Categories.repository.ISubCategoryRepository;
import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.User.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private AdminService adminService;

    @Mock
    private AdminControllerHelper adminControllerHelper;

    @Mock
    private ICategoryRepository iCategoryRepository;

    @Mock
    private ISubCategoryRepository iSubCategoryRepository;

    @Mock
    User mockUser = User
            .builder()
            .id(1L)
            .email("test@dal.ca")
            .password("test")
            .firstName("John")
            .lastName("Doe")
            .role(Role.ADMIN)
            .build();

    @Mock
    Category mockCategory = Category
            .builder()
            .categoryID(1L)
            .name("Test Category")
            .tax("15")
            .build();

    @Mock
    Category mockUpdateCategory = Category
            .builder()
            .categoryID(1L)
            .name("Test Update Category")
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

    @Mock
    SubCategory mockUpdateSubCategory = SubCategory
            .builder()
            .subCategoryID(1L)
            .name("Test Update Subcategory")
            .category(mockCategory)
            .build();

    @Mock
    SubCategoryRequest mockSubCategoryUpdateRequest = SubCategoryRequest
            .builder()
            .subCategoryID(1L)
            .name("Test Update SubCategory")
            .categoryID(mockCategory.getCategoryID())
            .isSubCategory(true)
            .build();

    @Test
    public void addCategoryTest() throws Exception {
        ResponseEntity<CategoryResponse> actualResponse;
        ResponseEntity<CategoryResponse> expectedResponse = ResponseEntity.ok(CategoryResponse.builder()
                .categories(List.of(mockCategory))
                .isSubCategory(false)
                .subject(mockUser.getEmail())
                .role(mockUser.getRole())
                .build());

        when(adminService.addCategory(mockCategory)).thenReturn(mockCategory);
        when(adminControllerHelper.createCategoryResponse(List.of(mockCategory))).thenReturn(expectedResponse);

        actualResponse = adminController.addCategory(mockCategory);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void updateCategoryTest() throws Exception {
        ResponseEntity<CategoryResponse> actualResponse;
        ResponseEntity<CategoryResponse> expectedResponse = ResponseEntity.ok(CategoryResponse.builder()
                .categories(List.of(mockUpdateCategory))
                .isSubCategory(false)
                .subject(mockUser.getEmail())
                .role(mockUser.getRole())
                .build());

        lenient().when(iCategoryRepository.findById(mockUpdateCategory.getCategoryID())).thenReturn(Optional.of(mockCategory));
        when(adminService.updateCategory(mockUpdateCategory)).thenReturn(mockUpdateCategory);
        when(adminControllerHelper.createCategoryResponse(List.of(mockUpdateCategory))).thenReturn(expectedResponse);

        actualResponse = adminController.updateCategory(mockUpdateCategory);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void deleteCategoryTest() throws Exception {
        ResponseEntity<CategoryResponse> actualResponse;
        ResponseEntity<CategoryResponse> expectedResponse = ResponseEntity.ok(CategoryResponse.builder()
                .isDeleted(true)
                .isSubCategory(false)
                .subject(mockUser.getEmail())
                .role(mockUser.getRole())
                .build());

        when(adminService.deleteCategory(mockCategory)).thenReturn(true);
        when(adminControllerHelper.deleteCategoryResponse(false, true)).thenReturn(expectedResponse);

        actualResponse = adminController.deleteCategory(mockCategory);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void addSubCategoryTest() throws Exception {
        ResponseEntity<CategoryResponse> actualResponse;
        ResponseEntity<CategoryResponse> expectedResponse = ResponseEntity.ok(CategoryResponse.builder()
                .subCategories(List.of(mockSubCategory))
                .isSubCategory(true)
                .subject(mockUser.getEmail())
                .role(mockUser.getRole())
                .build());

        when(adminService.addSubCategory(mockSubCategoryRequest)).thenReturn(mockSubCategory);
        when(adminControllerHelper.createSubCategoryResponse(List.of(mockSubCategory))).thenReturn(expectedResponse);

        actualResponse = adminController.addSubCategory(mockSubCategoryRequest);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void updateSubCategoryTest() throws Exception {
        ResponseEntity<CategoryResponse> actualResponse;
        ResponseEntity<CategoryResponse> expectedResponse = ResponseEntity.ok(CategoryResponse.builder()
                .subCategories(List.of(mockSubCategory))
                .isSubCategory(true)
                .subject(mockUser.getEmail())
                .role(mockUser.getRole())
                .build());

        lenient().when(iSubCategoryRepository.findById(mockSubCategoryRequest.getSubCategoryID())).thenReturn(Optional.of(mockSubCategory));
        when(adminService.updateSubCategory(mockSubCategoryUpdateRequest)).thenReturn(mockUpdateSubCategory);
        when(adminControllerHelper.createSubCategoryResponse(List.of(mockUpdateSubCategory))).thenReturn(expectedResponse);

        actualResponse = adminController.updateSubCategory(mockSubCategoryUpdateRequest);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void deleteSubCategoryTest() throws Exception {
        ResponseEntity<CategoryResponse> actualResponse;
        ResponseEntity<CategoryResponse> expectedResponse = ResponseEntity.ok(CategoryResponse.builder()
                .isDeleted(true)
                .isSubCategory(true)
                .subject(mockUser.getEmail())
                .role(mockUser.getRole())
                .build());

        when(adminService.deleteSubCategory(mockSubCategory)).thenReturn(true);
        when(adminControllerHelper.deleteCategoryResponse(true, true)).thenReturn(expectedResponse);

        actualResponse = adminController.deleteSubCategory(mockSubCategory);
        assertEquals(actualResponse, expectedResponse);
    }
}
