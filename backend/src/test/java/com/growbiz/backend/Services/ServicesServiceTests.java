package com.growbiz.backend.Services;

import com.growbiz.backend.Business.models.Business;
import com.growbiz.backend.Business.service.IBusinessService;
import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Categories.service.Sub.ISubCategoryService;
import com.growbiz.backend.Categories.service.Super.ICategoryService;
import com.growbiz.backend.RequestResponse.Services.ServiceRequest;
import com.growbiz.backend.Services.models.ServiceDTO;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.Services.repository.IServiceRepository;
import com.growbiz.backend.Services.service.ServicesService;
import com.growbiz.backend.TestConstants.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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

    ServiceDTO mockServiceDTO;

    Services mockServiceUpdated;

    Services nullService = new Services();

    @BeforeEach
    public void init() {
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
                .email(TestConstants.TEST_EMAIL)
                .build();
        mockServiceRequest = ServiceRequest
                .builder()
                .email(TestConstants.TEST_EMAIL)
                .serviceName(TestConstants.TEST_SERVICE_NAME)
                .description(TestConstants.TEST_SERVICE_DESCRIPTION)
                .price(TestConstants.TEST_SERVICE_PRICE)
                .businessID(TestConstants.TEST_ID_1)
                .subCategoryID(TestConstants.TEST_ID_1)
                .image(TestConstants.TEST_SERVICE_IMAGE_URL)
                .build();
        mockServiceDTO = ServiceDTO
                .builder()
                .serviceId(TestConstants.TEST_ID_1)
                .serviceName(TestConstants.TEST_SERVICE_NAME)
                .description(TestConstants.TEST_SERVICE_DESCRIPTION)
                .price(TestConstants.TEST_SERVICE_PRICE)
                .businessId(TestConstants.TEST_ID_1)
                .subCategoryId(TestConstants.TEST_ID_1)
                .imageURL(TestConstants.TEST_SERVICE_IMAGE_URL)
                .build();
        mockServiceUpdated = Services
                .builder()
                .serviceId(TestConstants.TEST_ID_1)
                .serviceName(TestConstants.TEST_SERVICE_NAME)
                .description(TestConstants.TEST_SERVICE_DESCRIPTION)
                .price(TestConstants.TEST_SERVICE_PRICE_UPDATED)
                .business(mockBusiness)
                .subCategory(mockSubCategory)
                .imageURL(TestConstants.TEST_SERVICE_IMAGE_URL)
                .build();
        mockService = Services
                .builder()
                .serviceId(TestConstants.TEST_ID_1)
                .serviceName(TestConstants.TEST_SERVICE_NAME)
                .description(TestConstants.TEST_SERVICE_DESCRIPTION)
                .price(TestConstants.TEST_SERVICE_PRICE)
                .imageURL(TestConstants.TEST_SERVICE_IMAGE_URL)
                .business(mockBusiness)
                .subCategory(mockSubCategory)
                .build();
    }

    @Test
    public void getServiceTest() {
        when(serviceRepository.findById(TestConstants.TEST_ID_1)).thenReturn(Optional.of(mockService));
        Services results = servicesService.getServiceById(TestConstants.TEST_ID_1);
        assertEquals(mockService, results);
    }

    @Test
    public void getNullServiceTest() {
        when((serviceRepository.findById(TestConstants.TEST_ID_1))).thenReturn(Optional.empty());
        Services results = servicesService.getServiceById(TestConstants.TEST_ID_1);
        assertEquals(null, results);
    }

    @Test
    public void getServiceByBusinessIdTest() {
        when(serviceRepository.findByBusinessBusinessId(TestConstants.TEST_ID_1)).thenReturn(List.of(mockService));
        List<Services> results1 = servicesService.getServiceByBusinessId(TestConstants.TEST_ID_1);
        assertEquals(List.of(mockService), results1);

        when(serviceRepository.findByBusinessBusinessId(TestConstants.TEST_ID_1)).thenThrow(new NullPointerException("Test Exception"));
        List<Services> results2 = servicesService.getServiceByBusinessId(TestConstants.TEST_ID_1);
        assertNull(results2);
    }

    @Test
    public void getServiceBySubCategoryIdTest() {
        when(serviceRepository.findBySubCategorySubCategoryID(TestConstants.TEST_ID_1)).thenReturn(List.of(mockService));
        List<Services> results1 = servicesService.getServiceBySubCategoryId(TestConstants.TEST_ID_1);
        assertEquals(List.of(mockService), results1);

        when(serviceRepository.findBySubCategorySubCategoryID(anyLong())).thenThrow(new NullPointerException("Test Exception"));
        List<Services> results2 = servicesService.getServiceBySubCategoryId(TestConstants.TEST_ID_1);
        assertNull(results2);
    }

    @Test
    public void getServicesByCategoryIdTest() {
        when(subCategoryService.fetchSubCategoryListForCategoryID(anyLong())).thenReturn(List.of(mockSubCategory, mockSubCategory));
        when(serviceRepository.findBySubCategorySubCategoryID(anyLong())).thenReturn(List.of(mockService));

        List<ServiceDTO> results = servicesService.getServicesByCategoryId(TestConstants.TEST_ID_1);

        assertEquals(List.of(mockServiceDTO, mockServiceDTO), results);
    }

    @Test
    public void getEmptyListOfServicesByCategoryIdTest() {
        when(subCategoryService.fetchSubCategoryListForCategoryID(mockCategory.getCategoryID())).thenReturn(null);
        List<ServiceDTO> results = servicesService.getServicesByCategoryId(TestConstants.TEST_ID_1);
        assertEquals(true, results.isEmpty());
    }

    @Test
    public void addNewServiceTest() {
        Services toBeSavedService = Services
                .builder()
                .serviceName(TestConstants.TEST_SERVICE_NAME)
                .description(TestConstants.TEST_SERVICE_DESCRIPTION)
                .price(TestConstants.TEST_SERVICE_PRICE)
                .imageURL(TestConstants.TEST_SERVICE_IMAGE_URL)
                .business(mockBusiness)
                .subCategory(mockSubCategory)
                .build();
        when(serviceRepository.findByServiceNameAndBusinessBusinessId(TestConstants.TEST_SERVICE_NAME, TestConstants.TEST_ID_1)).thenReturn(null);
        when(businessService.findById(TestConstants.TEST_ID_1)).thenReturn(mockBusiness);
        when(subCategoryService.getSubCategoryByID(TestConstants.TEST_ID_1)).thenReturn(mockSubCategory);
        doReturn(mockService).when(serviceRepository).save(toBeSavedService);
        Services results = servicesService.addService(mockServiceRequest);

        assertEquals(mockService, results);
        verify(serviceRepository, times(1)).save(any(Services.class));
    }

    @Test
    public void addExistingServiceTest() {
        when(serviceRepository.findByServiceNameAndBusinessBusinessId(TestConstants.TEST_SERVICE_NAME, TestConstants.TEST_ID_1)).thenReturn(mockService);
        Services results = servicesService.addService(mockServiceRequest);
        assertNull(results);
    }

    @Test
    public void updateExistingServiceTest() {
        ServiceRequest toBeUpdatedService = ServiceRequest
                .builder()
                .serviceName(TestConstants.TEST_SERVICE_NAME)
                .description(TestConstants.TEST_SERVICE_DESCRIPTION)
                .price(TestConstants.TEST_SERVICE_PRICE_UPDATED)
                .image(TestConstants.TEST_SERVICE_IMAGE_URL)
                .businessID(TestConstants.TEST_ID_1)
                .subCategoryID(TestConstants.TEST_ID_1)
                .build();
        when(serviceRepository.findById(anyLong())).thenReturn(Optional.of(mockService));
        when(businessService.findById(TestConstants.TEST_ID_1)).thenReturn(mockBusiness);
        when(subCategoryService.getSubCategoryByID(TestConstants.TEST_ID_1)).thenReturn(mockSubCategory);
        when(serviceRepository.save(mockServiceUpdated)).thenReturn(mockServiceUpdated);

        Services results = servicesService.updateService(toBeUpdatedService);

        assertEquals(mockServiceUpdated, results);
        verify(serviceRepository, times(1)).save(any(Services.class));
    }

    @Test
    public void updateNonExistingServiceTest() {
        Optional<Services> optionalService = Optional.empty();
        when(serviceRepository.findById(anyLong())).thenReturn(optionalService);
        Services results = servicesService.updateService(mockServiceRequest);
        assertNull(results);
    }

    @Test
    public void getTaxForServiceTest() {
        when(categoryService.getCategoryByID(TestConstants.TEST_ID_1)).thenReturn(mockCategory);
        String results = servicesService.getTaxForService(mockService);
        assertEquals(mockCategory.getTax(), results);
    }

    @Test
    public void getTaxForNonExistingServiceTest() {
        when(categoryService.getCategoryByID(TestConstants.TEST_ID_1)).thenThrow(new NullPointerException("Test Exception"));
        String results = servicesService.getTaxForService(mockService);
        assertNull(results);
    }

    @Test
    public void fetchAllServicesTest() {
        when(serviceRepository.findAll()).thenReturn(List.of(mockService));
        List<Services> services = servicesService.fetchServiceList();
        assertNotNull(services);
    }

    @Test
    public void fetchEmptyServicesTest() {
        when(serviceRepository.findAll()).thenThrow(new NullPointerException("Test Exception"));
        List<Services> services = servicesService.fetchServiceList();
        assertNull(services);
    }

    @Test
    public void deleteExistingServiceTest() {
        doNothing().when(serviceRepository).deleteById(mockService.getServiceId());
        Boolean result = servicesService.deleteService(mockService.getServiceId());
        verify(serviceRepository, times(1)).deleteById(mockService.getServiceId());
        assertTrue(result);
    }

    @Test
    public void deleteNonExistingServiceTest() {
        doThrow(new NullPointerException("Test Exception")).when(serviceRepository).deleteById(mockService.getServiceId());
        Boolean result = servicesService.deleteService(mockService.getServiceId());
        assertFalse(result);
    }
}