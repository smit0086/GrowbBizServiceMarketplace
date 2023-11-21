package com.growbiz.backend.Categories;

import com.growbiz.backend.Categories.helper.CategoryControllerHelper;
import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.RequestResponse.Category.CategoryResponse;
import com.growbiz.backend.TestConstants.TestConstants;
import com.growbiz.backend.User.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
public class CategoryControllerHelperTests {

    @InjectMocks
    private CategoryControllerHelper categoryControllerHelper;
    User mockUser;
    Category mockCategory;
    SubCategory mockSubCategory;
    Authentication authentication;
    SecurityContext securityContext;

    @BeforeEach
    public void init() {
        mockUser = User
                .builder()
                .id(1L)
                .email(TestConstants.TEST_EMAIL)
                .password(TestConstants.TEST_PASSWORD)
                .firstName("John")
                .lastName("Doe")
                .role(Role.CUSTOMER)
                .build();
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
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
    }

    @Test
    public void createCategoryResponse1Test() {
        ResponseEntity<CategoryResponse> expectedResponse = ResponseEntity.ok(
                CategoryResponse.builder()
                        .categories(List.of(mockCategory))
                        .isSubCategory(false)
                        .subject(mockUser.getEmail())
                        .role(Role.CUSTOMER)
                        .build()
        );
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);

        ResponseEntity<CategoryResponse> actualResponse = categoryControllerHelper.createCategoryResponse(List.of(mockCategory));
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void createSubCategoryResponseTest() {
        ResponseEntity<CategoryResponse> expectedResponse = ResponseEntity.ok(
                CategoryResponse.builder()
                        .subCategories(List.of(mockSubCategory))
                        .isSubCategory(true)
                        .subject(mockUser.getEmail())
                        .role(Role.CUSTOMER)
                        .build()
        );
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);

        ResponseEntity<CategoryResponse> actualResponse = categoryControllerHelper.createSubCategoryResponse(List.of(mockSubCategory));
        assertEquals(expectedResponse, actualResponse);

    }

    @Test
    public void createCategoryResponse2Test() {
        ResponseEntity<CategoryResponse> expectedResponse = ResponseEntity.ok(
                CategoryResponse.builder()
                        .categories(List.of(mockCategory))
                        .subCategories(List.of(mockSubCategory))
                        .subject(mockUser.getEmail())
                        .role(Role.CUSTOMER)
                        .build()
        );
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);

        ResponseEntity<CategoryResponse> actualResponse = categoryControllerHelper.createCategoryResponse(List.of(mockCategory),List.of(mockSubCategory));
        assertEquals(expectedResponse, actualResponse);

    }
}
