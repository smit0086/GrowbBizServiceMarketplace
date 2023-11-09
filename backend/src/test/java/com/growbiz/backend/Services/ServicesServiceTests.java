package com.growbiz.backend.Services;

import com.growbiz.backend.Business.model.Business;
import com.growbiz.backend.Business.service.IBusinessService;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Categories.service.Sub.ISubCategoryService;
import com.growbiz.backend.Categories.service.Super.ICategoryService;
import com.growbiz.backend.Services.controller.ServiceController;
import com.growbiz.backend.Services.helper.ServicesControllerHelper;
import com.growbiz.backend.Services.models.ServiceRequest;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.Services.repository.IServiceRepository;
import com.growbiz.backend.Services.service.IServicesService;
import com.growbiz.backend.Services.service.ServicesService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

}