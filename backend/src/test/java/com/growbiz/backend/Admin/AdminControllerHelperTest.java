package com.growbiz.backend.Admin;

import com.growbiz.backend.Admin.helper.AdminControllerHelper;
import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.CategoryResponse;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.User.models.Role;
import com.growbiz.backend.User.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminControllerHelperTest {
    @InjectMocks
    private AdminControllerHelper adminControllerHelper;

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
    SubCategory mockSubCategory = SubCategory
            .builder()
            .subCategoryID(1L)
            .name("Test Subcategory")
            .category(mockCategory)
            .build();

    @Test
    public void createCategoryResponseTest(){
        ResponseEntity<CategoryResponse> actualResponse;
        ResponseEntity<CategoryResponse> expectedResponse = ResponseEntity.ok(CategoryResponse.builder()
                .categories(List.of(mockCategory))
                .isSubCategory(false)
                .subject(mockUser.getEmail())
                .role(mockUser.getRole())
                .build());

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(mockUser);

        actualResponse = adminControllerHelper.createCategoryResponse(List.of(mockCategory));
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void createSubCategoryResponseTest(){
        ResponseEntity<CategoryResponse> actualResponse;
        ResponseEntity<CategoryResponse> expectedResponse = ResponseEntity.ok(CategoryResponse.builder()
                .subCategories(List.of(mockSubCategory))
                .isSubCategory(true)
                .subject(mockUser.getEmail())
                .role(mockUser.getRole())
                .build());

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(mockUser);

        actualResponse = adminControllerHelper.createSubCategoryResponse(List.of(mockSubCategory));
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void deleteCategoryResponseTest(){
        ResponseEntity<CategoryResponse> actualResponse;
        ResponseEntity<CategoryResponse> expectedResponse = ResponseEntity.ok(CategoryResponse.builder()
                .isDeleted(true)
                .isSubCategory(false)
                .subject(mockUser.getEmail())
                .role(mockUser.getRole())
                .build());

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(mockUser);

        actualResponse = adminControllerHelper.deleteCategoryResponse(false, true);
        assertEquals(actualResponse, expectedResponse);
    }
}
