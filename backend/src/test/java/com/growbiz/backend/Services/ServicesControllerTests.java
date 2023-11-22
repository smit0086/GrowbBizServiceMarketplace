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
                .build();
        mockService = Services
                .builder()
                .serviceId(1L)
                .serviceName(TestConstants.TEST_SERVICE_NAME)
                .description(TestConstants.TEST_SERVICE_DESCRIPTION)
                .price(TestConstants.TEST_SERVICE_PRICE)
                .business(mockBusiness)
                .subCategory(mockSubCategory)
                .build();
        mockServiceDTO = ServiceDTO
                .builder()
                .serviceId(1L)
                .serviceName(TestConstants.TEST_SERVICE_NAME)
                .description(TestConstants.TEST_SERVICE_DESCRIPTION)
                .price(TestConstants.TEST_SERVICE_PRICE)
                .businessId(1L)
                .subCategoryId(1L)
                .build();
        mockServiceRequest = ServiceRequest
                .builder()
                .serviceName(TestConstants.TEST_SERVICE_NAME)
                .description(TestConstants.TEST_SERVICE_DESCRIPTION)
                .price(TestConstants.TEST_SERVICE_PRICE)
                .businessID(1)
                .subCategoryID(1)
                .build();
        mockServiceUpdated = Services
                .builder()
                .serviceId(1L)
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
                ServiceResponse.builder().services(List.of(mockService)).isDeleted(false).role(Role.ADMIN).subject("testEmail@dal.ca").build());

        when(servicesHelper.createServiceResponse(List.of(mockService), false)).thenReturn(expectedResponse);

        ResponseEntity<ServiceResponse> resultResponse = serviceController.getAllServices();
        assertEquals(expectedResponse, resultResponse);
    }

    @Test
    public void getExistingServiceTest() {
        when(servicesService.getServiceById(1L)).thenReturn(mockService);
        when(servicesService.getTaxForService(mockService)).thenReturn(TestConstants.TEST_CATEGORY_TAX);

        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder().services(List.of(mockService)).tax(TestConstants.TEST_CATEGORY_TAX).role(Role.ADMIN).subject(TestConstants.TEST_EMAIL).build());

        when(servicesHelper.createServiceResponseWithTax(List.of(mockService), TestConstants.TEST_CATEGORY_TAX)).thenReturn(expectedResponse);

        ResponseEntity<ServiceResponse> resultResponse = serviceController.getService(1L);
        assertEquals(expectedResponse, resultResponse);
    }

    @Test
    public void getNonExistingServiceTest() {
        when(servicesService.getServiceById(1L)).thenReturn(null);

        assertThrows(ServiceNotFoundException.class, () -> {
            serviceController.getService(1L);
        });
    }

    @Test
    public void getAllServicesListForBusinessTest() {
        when(servicesService.getServiceByBusinessId(1L)).thenReturn(List.of(mockService));

        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder().services(List.of(mockService)).isDeleted(false).role(Role.ADMIN).subject(TestConstants.TEST_EMAIL).build());

        when(servicesHelper.createServiceResponse(List.of(mockService), false)).thenReturn(expectedResponse);

        ResponseEntity<ServiceResponse> resultResponse = serviceController.getAllServicesByBusinessId(1L);
        assertEquals(expectedResponse, resultResponse);
    }

    @Test
    public void getAllServicesListForSubCategoryTest() {
        when(servicesService.getServiceBySubCategoryId(1L)).thenReturn(List.of(mockService));

        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder().services(List.of(mockService)).isDeleted(false).role(Role.ADMIN).subject(TestConstants.TEST_EMAIL).build());

        when(servicesHelper.createServiceResponse(List.of(mockService), false)).thenReturn(expectedResponse);

        ResponseEntity<ServiceResponse> resultResponse = serviceController.getAllServicesBySubCategoryId(1L);
        assertEquals(expectedResponse, resultResponse);
    }

    @Test
    public void getAllServicesListForCategoryTest() {
        when(servicesService.getServicesByCategoryId(1L)).thenReturn(List.of(mockServiceDTO));

        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder().serviceDTOS(List.of(mockServiceDTO)).isDeleted(false).role(Role.ADMIN).subject(TestConstants.TEST_EMAIL).build());

        when(servicesHelper.createServiceDTOResponse(List.of(mockServiceDTO), false)).thenReturn(expectedResponse);

        ResponseEntity<ServiceResponse> resultResponse = serviceController.getAllServicesByCategoryId(1L);
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