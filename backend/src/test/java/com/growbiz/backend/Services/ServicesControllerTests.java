package com.growbiz.backend.Services;

import com.growbiz.backend.Business.models.Business;
import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.Exception.exceptions.Service.ServiceAlreadyExistsException;
import com.growbiz.backend.Exception.exceptions.Service.ServiceNotFoundException;
import com.growbiz.backend.RequestResponse.Services.ServiceRequest;
import com.growbiz.backend.RequestResponse.Services.ServiceResponse;
import com.growbiz.backend.Services.controller.ServiceController;
import com.growbiz.backend.Services.helper.ServicesControllerHelper;
import com.growbiz.backend.Services.models.ServiceDTO;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.Services.service.IServicesService;
import com.growbiz.backend.TestConstants.TestConstants;
import org.junit.Before;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServicesControllerTests {
    @Mock
    private IServicesService servicesService;
    @Mock
    private ServicesControllerHelper servicesHelper;
    @InjectMocks
    private ServiceController serviceController;
    @Mock
    Category mockCategory;
    @Mock
    Business mockBusiness;
    @Mock
    SubCategory mockSubCategory;
    @Mock
    ServiceRequest mockServiceRequest;
    @Mock
    Services mockService;

    @Mock
    ServiceDTO mockServiceDTO;
    @Mock
    Services mockServiceUpdated;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        mockCategory = Category
                .builder()
                .categoryID(TestConstants.TEST_ID_1)
                .name(TestConstants.TEST_CATEGORY_NAME)
                .tax(TestConstants.TEST_CATEGORY_TAX)
                .build();
        mockSubCategory = SubCategory
                .builder()
                .subCategoryID(TestConstants.TEST_ID_1)
                .name(TestConstants.TEST_SUBCATEGORY_NAME)
                .category(mockCategory)
                .build();
        mockBusiness = Business
                .builder()
                .businessId(TestConstants.TEST_ID_1)
                .businessName(TestConstants.TEST_BUSINESS_NAME)
                .build();
        mockService = Services
                .builder()
                .serviceId(TestConstants.TEST_ID_1)
                .serviceName(TestConstants.TEST_SERVICE_NAME)
                .description(TestConstants.TEST_SERVICE_DESCRIPTION)
                .price(TestConstants.TEST_SERVICE_PRICE)
                .business(mockBusiness)
                .subCategory(mockSubCategory)
                .build();
        mockServiceDTO = ServiceDTO
                .builder()
                .serviceId(TestConstants.TEST_ID_1)
                .serviceName(TestConstants.TEST_SERVICE_NAME)
                .description(TestConstants.TEST_SERVICE_DESCRIPTION)
                .price(TestConstants.TEST_SERVICE_PRICE)
                .businessId(TestConstants.TEST_ID_1)
                .subCategoryId(TestConstants.TEST_ID_1)
                .build();
        mockServiceRequest = ServiceRequest
                .builder()
                .serviceName(TestConstants.TEST_SERVICE_NAME)
                .description(TestConstants.TEST_SERVICE_DESCRIPTION)
                .price(TestConstants.TEST_SERVICE_PRICE)
                .businessID(TestConstants.TEST_ID_1)
                .subCategoryID(TestConstants.TEST_ID_1)
                .build();
        mockServiceUpdated = Services
                .builder()
                .serviceId(TestConstants.TEST_ID_1)
                .serviceName(TestConstants.TEST_SERVICE_NAME)
                .description(TestConstants.TEST_SERVICE_DESCRIPTION)
                .price(TestConstants.TEST_SERVICE_PRICE_UPDATED)
                .business(mockBusiness)
                .subCategory(mockSubCategory)
                .build();
    }

    @Test
    public void getAllServicesListTest() {
        when(servicesService.fetchServiceList()).thenReturn(List.of(mockService));

        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder().services(List.of(mockService)).isDeleted(false).role(Role.ADMIN).subject(TestConstants.TEST_EMAIL).build());

        when(servicesHelper.createServiceResponse(List.of(mockService), false)).thenReturn(expectedResponse);

        ResponseEntity<ServiceResponse> resultResponse = serviceController.getAllServices();
        assertEquals(expectedResponse, resultResponse);
    }

    @Test
    public void getExistingServiceTest() {
        when(servicesService.getServiceById(TestConstants.TEST_ID_1)).thenReturn(mockService);
        when(servicesService.getTaxForService(mockService)).thenReturn(TestConstants.TEST_CATEGORY_TAX);

        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder().services(List.of(mockService)).tax(TestConstants.TEST_CATEGORY_TAX).role(Role.ADMIN).subject(TestConstants.TEST_EMAIL).build());

        when(servicesHelper.createServiceResponseWithTax(List.of(mockService), TestConstants.TEST_CATEGORY_TAX)).thenReturn(expectedResponse);

        ResponseEntity<ServiceResponse> resultResponse = serviceController.getService(TestConstants.TEST_ID_1);
        assertEquals(expectedResponse, resultResponse);
    }

    @Test
    public void getNonExistingServiceTest() {
        when(servicesService.getServiceById(TestConstants.TEST_ID_1)).thenReturn(null);

        assertThrows(ServiceNotFoundException.class, () -> {
            serviceController.getService(TestConstants.TEST_ID_1);
        });
    }

    @Test
    public void getAllServicesListForBusinessTest() {
        when(servicesService.getServiceByBusinessId(TestConstants.TEST_ID_1)).thenReturn(List.of(mockService));

        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder().services(List.of(mockService)).isDeleted(false).role(Role.ADMIN).subject(TestConstants.TEST_EMAIL).build());

        when(servicesHelper.createServiceResponse(List.of(mockService), false)).thenReturn(expectedResponse);

        ResponseEntity<ServiceResponse> resultResponse = serviceController.getAllServicesByBusinessId(TestConstants.TEST_ID_1);
        assertEquals(expectedResponse, resultResponse);
    }

    @Test
    public void getAllServicesListForSubCategoryTest() {
        when(servicesService.getServiceBySubCategoryId(TestConstants.TEST_ID_1)).thenReturn(List.of(mockService));

        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder().services(List.of(mockService)).isDeleted(false).role(Role.ADMIN).subject(TestConstants.TEST_EMAIL).build());

        when(servicesHelper.createServiceResponse(List.of(mockService), false)).thenReturn(expectedResponse);

        ResponseEntity<ServiceResponse> resultResponse = serviceController.getAllServicesBySubCategoryId(TestConstants.TEST_ID_1);
        assertEquals(expectedResponse, resultResponse);
    }

    @Test
    public void getAllServicesListForCategoryTest() {
        when(servicesService.getServicesByCategoryId(TestConstants.TEST_ID_1)).thenReturn(List.of(mockServiceDTO));

        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder().serviceDTOS(List.of(mockServiceDTO)).isDeleted(false).role(Role.ADMIN).subject(TestConstants.TEST_EMAIL).build());

        when(servicesHelper.createServiceDTOResponse(List.of(mockServiceDTO), false)).thenReturn(expectedResponse);

        ResponseEntity<ServiceResponse> resultResponse = serviceController.getAllServicesByCategoryId(TestConstants.TEST_ID_1);
        assertEquals(expectedResponse, resultResponse);
    }

    @Test
    public void addServiceTest() {
        when(servicesService.addService(mockServiceRequest)).thenReturn(mockService);

        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder().isDeleted(false).role(Role.ADMIN).subject(TestConstants.TEST_EMAIL).build());

        when(servicesHelper.createServiceResponse(List.of(mockService), false)).thenReturn(expectedResponse);

        ResponseEntity<ServiceResponse> resultResponse = serviceController.addService(mockServiceRequest);
        assertEquals(expectedResponse, resultResponse);
    }

    @Test
    public void addExistingServiceTest() {
        when(servicesService.addService(mockServiceRequest)).thenReturn(null);

        assertThrows(ServiceAlreadyExistsException
                .class, () -> {
            serviceController.addService(mockServiceRequest);
        });
    }

    @Test
    public void updateServiceTest() {
        when(servicesService.updateService(mockServiceRequest)).thenReturn(mockService);

        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder().isDeleted(false).role(Role.ADMIN).subject(TestConstants.TEST_EMAIL).build());

        when(servicesHelper.createServiceResponse(List.of(mockService), true)).thenReturn(expectedResponse);

        ResponseEntity<ServiceResponse> resultResponse = serviceController.updateService(mockServiceRequest);
        assertEquals(expectedResponse, resultResponse);
    }

    @Test
    public void updateExistingServiceTest() {
        when(servicesService.updateService(mockServiceRequest)).thenReturn(null);

        assertThrows(ServiceNotFoundException
                .class, () -> {
            serviceController.updateService(mockServiceRequest);
        });
    }

    @Test
    public void deleteExistingServiceTest() {
        when(servicesService.deleteService(mockService.getServiceId())).thenReturn(true);

        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder().isDeleted(true).role(Role.ADMIN).subject(TestConstants.TEST_EMAIL).build());

        when(servicesHelper.deleteServiceResponse(true)).thenReturn(expectedResponse);

        ResponseEntity<ServiceResponse> resultResponse = serviceController.deleteService(mockService);
        assertEquals(expectedResponse, resultResponse);
    }

    @Test
    public void deleteNonExistingServiceTest() {
        when(servicesService.deleteService(mockService.getServiceId())).thenReturn(false);

        assertThrows(ServiceNotFoundException.class, () -> {
            serviceController.deleteService(mockService);
        });
    }
}