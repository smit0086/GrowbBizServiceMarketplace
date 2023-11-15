package com.growbiz.backend.Services;

import com.growbiz.backend.Business.model.Business;
import com.growbiz.backend.Business.service.IBusinessService;
import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Categories.service.Sub.ISubCategoryService;
import com.growbiz.backend.Categories.service.Super.ICategoryService;
import com.growbiz.backend.Services.models.ServiceRequest;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.Services.repository.IServiceRepository;
import com.growbiz.backend.Services.service.ServicesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServicesServiceTests {
    @InjectMocks
    private ServicesService servicesService;
    @Mock
    private IServiceRepository serviceRepository;
    @Mock
    private ISubCategoryService subCategoryService;
    @Mock
    private ICategoryService categoryService;
    @Mock
    private IBusinessService businessService;

    Business mockBusiness;

    Category mockCategory;

    SubCategory mockSubCategory;

    ServiceRequest mockServiceRequest;

    Services mockService;

    Services mockServiceUpdated;

    @BeforeEach
    public void init() {
//        MockitoAnnotations.openMocks(this);
        mockCategory = Category
                .builder()
                .categoryID(1L)
                .name("Beauty & Aesthetics")
                .tax("15")
                .build();
        mockSubCategory = SubCategory
                .builder()
                .subCategoryID(1L)
                .name("Manicure")
                .category(mockCategory)
                .build();
        mockBusiness = Business
                .builder()
                .businessId(1L)
                .businessName("French Nails")
                .build();

        mockServiceRequest = ServiceRequest
                .builder()
                .serviceName("Nail Care")
                .description("Loren Epsom")
                .price(24.00)
                .businessID(1)
                .subCategoryID(1)
                .build();
        mockServiceUpdated = Services
                .builder()
                .serviceId(1L)
                .serviceName("Hands and Nail Care")
                .description("Loren Epsom")
                .price(32.00)
                .business(mockBusiness)
                .subCategory(mockSubCategory)
                .build();
        mockService = Services
                .builder()
                .serviceId(1L)
                .serviceName("Nail Care")
                .description("Loren Epsom")
                .price(24.00)
                .business(mockBusiness)
                .subCategory(mockSubCategory)
                .build();
    }

    @Test
    public void getServiceTest() {
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(mockService));
        Services results = servicesService.getServiceById(1L);
        assertEquals(mockService,results);
    }

    @Test
    public void getNullServiceTest() {
        when((serviceRepository.findById(1L))).thenReturn(Optional.empty());
        Services results = servicesService.getServiceById(1L);
        assertEquals(null,results);
    }

    @Test
    public void getServiceByBusinessIdTest() {
        when(serviceRepository.findByBusinessBusinessId(1L)).thenReturn(List.of(mockService));
        List<Services> results1 = servicesService.getServiceByBusinessId(1L);
        assertEquals(List.of(mockService),results1);

        when(serviceRepository.findByBusinessBusinessId(1L)).thenThrow(new NullPointerException("Test Exception"));
        List<Services> results2 = servicesService.getServiceByBusinessId(1L);
        assertNull(results2);
    }

    @Test
    public void getServiceBySubCategoryIdTest() {
        when(serviceRepository.findBySubCategorySubCategoryID(1L)).thenReturn(List.of(mockService));
        List<Services> results1 = servicesService.getServiceBySubCategoryId(1L);
        assertEquals(List.of(mockService),results1);

        when(serviceRepository.findBySubCategorySubCategoryID(1L)).thenThrow(new NullPointerException("Test Exception"));
        List<Services> results2 = servicesService.getServiceBySubCategoryId(1L);
        assertNull(results2);
    }

    @Test
    public void getTaxForServiceTest() {
        when(categoryService.getCategoryByID(1L)).thenReturn(mockCategory);
        String results = servicesService.getTaxForService(mockService);
        assertEquals(mockCategory.getTax(), results);
    }

    @Test
    public void getTaxForNonExistingServiceTest() {
        when(categoryService.getCategoryByID(1L)).thenThrow(new NullPointerException("Test Exception"));
        String results = servicesService.getTaxForService(mockService);
        assertNull(results);
    }

//    @Test
//    public void fetchServiceListTest() {
//
//    }
//
//    @Test
//    public void addServiceTest() {
//
//    }
//    @Test
//    public void addExistingServiceTest() {
//
//    }
//
//    @Test
//    public void updateExistingServiceTest() {
//
//    }
//
//    @Test
//    public void updateNonExistingServiceTest() {
//
//    }
//
//    @Test
//    public void deleteExistingServiceTest() {
//
//    }
//
//    @Test
//    public void getAllServicesForBusinessTest() {
//
//    }
//    @Test
//    public void getAllServicesForSubCategoryTest() {
//
//    }
}