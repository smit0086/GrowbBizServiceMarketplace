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
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ServicesServiceTests {
    @Mock
    private IServiceRepository serviceRepository;
    @Mock
    private ISubCategoryService subCategoryService;
    @Mock
    private ICategoryService categoryService;
    @Mock
    private IBusinessService businessService;
    @InjectMocks
    private ServicesService servicesService;
    @Mock
    Business mockBusiness = Business
            .builder()
            .businessId(1L)
            .businessName("French Nails")
            .build();
    @Mock
    Category mockCategory = Category
            .builder()
            .categoryID(1L)
            .name("Beauty & Aesthetics")
            .tax("15")
            .build();
    @Mock
    SubCategory mockSubCategory = SubCategory
            .builder()
            .subCategoryID(1L)
            .name("Manicure")
            .category(mockCategory)
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

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getServiceSuccessTest() {
        when(serviceRepository.findById(1L)).thenReturn(Optional.ofNullable(mockService));
        Services results = servicesService.getServiceById(1L);
        assertEquals(mockService,results);
    }

    @Test
    public void getServiceByBusinessIdSuccessTest() {
        when(serviceRepository.findByBusinessBusinessId(1L)).thenReturn(List.of(mockService));
        List<Services> results = servicesService.getServiceByBusinessId(1L);
        assertEquals(List.of(mockService),results);
    }

    @Test
    public void getServiceBySubCategoryIdSuccessTest() {
        when(serviceRepository.findBySubCategorySubCategoryID(1L)).thenReturn(List.of(mockService));
        List<Services> results = servicesService.getServiceBySubCategoryId(1L);
        assertEquals(List.of(mockService),results);
    }

    @Test
    public void getTaxForServiceSuccessTest() {
        when(mockService.getSubCategory()).thenReturn(mockSubCategory);
        when(categoryService.getCategoryByID(1L)).thenReturn(mockCategory);
        String results = servicesService.getTaxForService(mockService);
        assertEquals(mockCategory.getTax(), results);
    }
}