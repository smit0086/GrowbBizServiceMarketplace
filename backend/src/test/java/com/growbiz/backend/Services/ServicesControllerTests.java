package com.growbiz.backend.Services;

import com.growbiz.backend.Business.model.Business;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Exception.exceptions.ServiceAlreadyExistsException;
import com.growbiz.backend.Exception.exceptions.ServiceNotFoundException;
import com.growbiz.backend.Services.controller.ServiceController;
import com.growbiz.backend.Services.helper.ServicesControllerHelper;
import com.growbiz.backend.Services.models.ServiceRequest;
import com.growbiz.backend.Services.models.ServiceResponse;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.Services.service.IServicesService;
import com.growbiz.backend.User.models.Role;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ServicesControllerTests {

    @Mock
    private IServicesService servicesService;
    @Mock
    private ServicesControllerHelper servicesHelper;
    @InjectMocks
    private ServiceController serviceController;
    @Mock
    Business mockBusiness = Business
            .builder()
            .businessId(1L)
            .businessName("French Nails")
            .build();

    @Mock
    SubCategory mockSubCategory = SubCategory
            .builder()
            .subCategoryID(1L)
            .name("Manicure")
            .subCategoryID(2L)
            .build();

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addServiceSuccessTest() {
        ServiceRequest mockServiceToAdd = ServiceRequest
                .builder()
                .serviceName("Nail Care")
                .description("Loren Epsom")
                .price(24.00)
                .businessID(1)
                .subCategoryID(1)
                .build();

        Services mockServiceAdded = Services
                .builder()
                .serviceId(1L)
                .serviceName("Nail Care")
                .description("Loren Epsom")
                .price(24.00)
                .business(mockBusiness)
                .subCategory(mockSubCategory)
                .build();

        when(servicesService.addService(mockServiceToAdd)).thenReturn(mockServiceAdded);

        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder().services(List.of(mockServiceAdded)).isUpdated(false).role(Role.ADMIN).subject("testEmail@dal.ca").build());

        when(servicesHelper.createServiceResponse(List.of(mockServiceAdded), false)).thenReturn(expectedResponse);

        ResponseEntity<ServiceResponse> resultResponse = serviceController.addService(mockServiceToAdd);
        assertEquals(expectedResponse, resultResponse);
    }

    @Test
    public void addExistingServiceSuccessTest() {
        ServiceRequest mockServiceToAdd = ServiceRequest
                .builder()
                .serviceName("Nail Care")
                .description("Loren Epsom")
                .price(24.00)
                .businessID(1)
                .subCategoryID(1)
                .build();

        when(servicesService.addService(mockServiceToAdd)).thenReturn(null);

        assertThrows(ServiceAlreadyExistsException.class , () -> {
            serviceController.addService(mockServiceToAdd);
        });
    }

    @Test
    public void updateExistingServiceSuccessTest() {

    }

    @Test
    public void updateNonExistingServiceSuccessTest() {

    }

    @Test
    public void deleteServiceSuccessTest() {
        Services mockServiceToDelete = Services
                .builder()
                .serviceId(1L)
                .serviceName("Nail Care")
                .description("Loren Epsom")
                .price(24.00)
                .business(mockBusiness)
                .subCategory(mockSubCategory)
                .build();

        when(servicesService.deleteService(mockServiceToDelete.getServiceId())).thenReturn(true);

        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder().isDeleted(true).role(Role.ADMIN).subject("testEmail@dal.ca").build());

        when(servicesHelper.deleteServiceResponse(true)).thenReturn(expectedResponse);

        ResponseEntity<ServiceResponse> resultResponse = serviceController.deleteService(mockServiceToDelete);
        assertEquals(expectedResponse, resultResponse);
    }

    @Test
    public void deleteNonExistingServiceTest() {
        Services mockServiceToDelete = Services
                .builder()
                .serviceId(0L)
                .serviceName("Nail Care")
                .description("Loren Epsom")
                .price(24.00)
                .business(mockBusiness)
                .subCategory(mockSubCategory)
                .build();

        when(servicesService.deleteService(mockServiceToDelete.getServiceId())).thenReturn(false);

        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder().isDeleted(false).role(Role.ADMIN).subject("testEmail@dal.ca").build());

        when(servicesHelper.deleteServiceResponse(false)).thenReturn(expectedResponse);

        assertThrows(ServiceNotFoundException.class , () -> {
            serviceController.deleteService(mockServiceToDelete);
        });
    }
}