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
    @Mock
    ServiceRequest mockServiceRequest = ServiceRequest
            .builder()
            .serviceName("Nail Care")
            .description("Loren Epsom")
            .price(24.00)
            .businessID(1)
            .subCategoryID(1)
            .build();
    @Mock
    Services mockService = Services
            .builder()
            .serviceId(1L)
            .serviceName("Nail Care")
            .description("Loren Epsom")
            .price(24.00)
            .business(mockBusiness)
            .subCategory(mockSubCategory)
            .build();

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getExistingServiceSuccessTest() {
        when(servicesService.getServiceById(1L)).thenReturn(mockService);
        when(servicesService.getTaxForService(mockService)).thenReturn("15");

        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder().services(List.of(mockService)).tax("15").role(Role.ADMIN).subject("testEmail@dal.ca").build());

        when(servicesHelper.createServiceResponseWithTax(List.of(mockService), "15")).thenReturn(expectedResponse);

        ResponseEntity<ServiceResponse> resultResponse = serviceController.getService(1L);
        assertEquals(expectedResponse, resultResponse);
    }

    @Test
    public void getNonExistingServiceSuccessTest() {
        when(servicesService.getServiceById(1L)).thenReturn(null);
        assertThrows(ServiceNotFoundException.class , () -> {
            serviceController.getService(1L);
        });
    }


    @Test
    public void addServiceSuccessTest() {
        when(servicesService.addService(mockServiceRequest)).thenReturn(mockService);

        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder().services(List.of(mockService)).isUpdated(false).role(Role.ADMIN).subject("testEmail@dal.ca").build());

        when(servicesHelper.createServiceResponse(List.of(mockService), false)).thenReturn(expectedResponse);

        ResponseEntity<ServiceResponse> resultResponse = serviceController.addService(mockServiceRequest);
        assertEquals(expectedResponse, resultResponse);
    }

    @Test
    public void addExistingServiceSuccessTest() {
        when(servicesService.addService(mockServiceRequest)).thenReturn(null);

        assertThrows(ServiceAlreadyExistsException.class , () -> {
            serviceController.addService(mockServiceRequest);
        });
    }

    @Test
    public void updateExistingServiceSuccessTest() {
        Services mockServiceUpdated = Services
                .builder()
                .serviceId(1L)
                .serviceName("Hands and Nail Care")
                .description("Loren Epsom")
                .price(32.00)
                .business(mockBusiness)
                .subCategory(mockSubCategory)
                .build();

        when(servicesService.updateService(mockServiceRequest)).thenReturn(mockServiceUpdated);

        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder().services(List.of(mockServiceUpdated)).isUpdated(true).role(Role.ADMIN).subject("testEmail@dal.ca").build());

        when(servicesHelper.createServiceResponse(List.of(mockServiceUpdated), true)).thenReturn(expectedResponse);

        ResponseEntity<ServiceResponse> resultResponse = serviceController.updateService(mockServiceRequest);
        assertEquals(expectedResponse, resultResponse);
    }

    @Test
    public void updateNonExistingServiceSuccessTest() {
        when(servicesService.updateService(mockServiceRequest)).thenReturn(null);

        assertThrows(ServiceNotFoundException.class , () -> {
            serviceController.updateService(mockServiceRequest);
        });
    }

    @Test
    public void deleteServiceSuccessTest() {
        when(servicesService.deleteService(mockService.getServiceId())).thenReturn(true);

        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder().isDeleted(true).role(Role.ADMIN).subject("testEmail@dal.ca").build());

        when(servicesHelper.deleteServiceResponse(true)).thenReturn(expectedResponse);

        ResponseEntity<ServiceResponse> resultResponse = serviceController.deleteService(mockService);
        assertEquals(expectedResponse, resultResponse);
    }

    @Test
    public void deleteNonExistingServiceTest() {
        when(servicesService.deleteService(mockService.getServiceId())).thenReturn(false);

        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder().isDeleted(false).role(Role.ADMIN).subject("testEmail@dal.ca").build());

        when(servicesHelper.deleteServiceResponse(false)).thenReturn(expectedResponse);

        assertThrows(ServiceNotFoundException.class , () -> {
            serviceController.deleteService(mockService);
        });
    }
}