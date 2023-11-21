package com.growbiz.backend.Services;

import com.growbiz.backend.Business.models.Business;
import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.RequestResponse.Services.ServiceResponse;
import com.growbiz.backend.Services.helper.ServicesControllerHelper;
import com.growbiz.backend.Services.models.ServiceDTO;
import com.growbiz.backend.Services.models.Services;
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
public class ServicesControllerHelperTests {

    @InjectMocks
    private ServicesControllerHelper servicesControllerHelper;
    User mockUser;
    Authentication authentication;
    SecurityContext securityContext;
    Business mockBusiness;

    Category mockCategory;

    SubCategory mockSubCategory;
    Services mockService;

    ServiceDTO mockServiceDTO;

    @BeforeEach
    public void init() {
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        mockUser = User
                .builder()
                .id(1L)
                .email(TestConstants.TEST_EMAIL)
                .password(TestConstants.TEST_PASSWORD)
                .firstName("John")
                .lastName("Doe")
                .role(Role.CUSTOMER)
                .build();
        mockServiceDTO = ServiceDTO
                .builder()
                .serviceId(1L)
                .serviceName(TestConstants.TEST_SERVICE_NAME)
                .description(TestConstants.TEST_SERVICE_DESCRIPTION)
                .price(TestConstants.TEST_SERVICE_PRICE)
                .businessId(1L)
                .subCategoryId(1L)
                .imageURL(TestConstants.TEST_SERVICE_IMAGE_URL)
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
        mockBusiness = Business
                .builder()
                .businessId(1L)
                .businessName(TestConstants.TEST_BUSINESS_NAME)
                .email(TestConstants.TEST_EMAIL)
                .build();
        mockService = Services
                .builder()
                .serviceId(1L)
                .serviceName(TestConstants.TEST_SERVICE_NAME)
                .description(TestConstants.TEST_SERVICE_DESCRIPTION)
                .price(TestConstants.TEST_SERVICE_PRICE)
                .imageURL(TestConstants.TEST_SERVICE_IMAGE_URL)
                .business(mockBusiness)
                .subCategory(mockSubCategory)
                .build();
    }

    @Test
    public void createServiceResponseTest() {
        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder()
                        .services(List.of(mockService))
                        .isUpdated(true)
                        .subject(mockUser.getEmail())
                        .role(Role.CUSTOMER)
                        .build()
        );
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);

        ResponseEntity<ServiceResponse> actualResponse = servicesControllerHelper.createServiceResponse(List.of(mockService),true);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void createServiceDTOResponseTest() {
        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder()
                        .serviceDTOS(List.of(mockServiceDTO))
                        .isUpdated(true)
                        .subject(mockUser.getEmail())
                        .role(Role.CUSTOMER)
                        .build()
        );
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);

        ResponseEntity<ServiceResponse> actualResponse = servicesControllerHelper.createServiceDTOResponse(List.of(mockServiceDTO),true);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void deleteServiceResponseTest() {
        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder()
                        .isDeleted(true)
                        .subject(mockUser.getEmail())
                        .role(Role.CUSTOMER)
                        .build()
        );
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);

        ResponseEntity<ServiceResponse> actualResponse = servicesControllerHelper.deleteServiceResponse(true);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void createServiceResponseWithTaxTest() {
        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder()
                        .services(List.of(mockService))
                        .tax(TestConstants.TEST_CATEGORY_TAX)
                        .businessId(mockBusiness.getBusinessId())
                        .subject(mockUser.getEmail())
                        .role(Role.CUSTOMER)
                        .build()
        );
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);

        ResponseEntity<ServiceResponse> actualResponse = servicesControllerHelper.createServiceResponseWithTax(List.of(mockService),"12.2");
        assertEquals(expectedResponse, actualResponse);
    }
}
