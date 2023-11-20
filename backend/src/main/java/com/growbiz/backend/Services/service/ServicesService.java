package com.growbiz.backend.Services.service;

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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServicesService implements IServicesService {

    @Autowired
    private final IServiceRepository iServiceRepository;

    @Autowired
    private final ISubCategoryService subCategoryService;

    @Autowired
    private final ICategoryService iCategoryService;

    @Autowired
    private final IBusinessService businessService;

    @Override
    public Services getServiceById(Long serviceId) {
        Optional<Services> service = iServiceRepository.findById(serviceId);
        if (service.isPresent()) {
            return service.get();
        } else {
            return null;
        }
    }

    @Override
    public List<Services> getServiceByBusinessId(Long businessId) {
        try {
            return iServiceRepository.findByBusinessBusinessId(businessId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Services> getServiceBySubCategoryId(Long subCategoryId) {
        try {
            return iServiceRepository.findBySubCategorySubCategoryID(subCategoryId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ServiceDTO> getServicesByCategoryId(Long categoryId) {
        List<ServiceDTO> services = new ArrayList<>();
        List<SubCategory> subCategoryList = subCategoryService.fetchSubCategoryListForCategoryID(categoryId);
        if (Objects.nonNull(subCategoryList)) {
            for (SubCategory subcategory : subCategoryList) {
                List<Services> s = iServiceRepository.findBySubCategorySubCategoryID(subcategory.getSubCategoryID());
                for (Services service : s) {
                    ServiceDTO serviceDTO = ServiceDTO.builder()
                            .serviceId(service.getServiceId())
                            .serviceName(service.getServiceName())
                            .description(service.getDescription())
                            .price(service.getPrice())
                            .timeRequired(service.getTimeRequired())
                            .imageURL(service.getImageURL())
                            .subCategoryId(subcategory.getSubCategoryID())
                            .businessId(service.getBusiness().getBusinessId())
                            .build();
                    services.add(serviceDTO);
                }
            }
        }
        return services;
    }

    @Override
    public String getTaxForService(Services service) {
        try {
            SubCategory subCategory = service.getSubCategory();
            Long categoryID = subCategory.getCategory().getCategoryID();
            Category category = iCategoryService.getCategoryByID(categoryID);
            return category.getTax();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Services> fetchServiceList() {
        try {
            return (List<Services>) iServiceRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Services addService(ServiceRequest newServiceRequest) {
        Services existingService = iServiceRepository.findByServiceNameAndBusinessBusinessId(
                newServiceRequest.getServiceName(),
                newServiceRequest.getBusinessID());
        if (Objects.isNull(existingService)) {
            Business business = businessService.findById(newServiceRequest.getBusinessID());
            SubCategory subCategory = subCategoryService.getSubCategoryByID(newServiceRequest.getSubCategoryID());
            Services service = Services.builder()
                    .serviceName(newServiceRequest.getServiceName())
                    .description(newServiceRequest.getDescription())
                    .price(newServiceRequest.getPrice())
                    .timeRequired(newServiceRequest.getTimeRequired())
                    .business(business)
                    .subCategory(subCategory)
                    .imageURL(newServiceRequest.getImage())
                    .build();
            return iServiceRepository.save(service);
        }
        return null;
    }

    @Override
    public Services updateService(ServiceRequest changeServiceRequest) {
        Optional<Services> serviceToUpdate = iServiceRepository.findById(changeServiceRequest.getServiceID());
        if (serviceToUpdate.isEmpty()) {
            return null;
        } else {
            Business business = businessService.findById(changeServiceRequest.getBusinessID());
            SubCategory subCategory = subCategoryService.getSubCategoryByID(changeServiceRequest.getSubCategoryID());
            Services serviceUpdated = Services.builder()
                    .serviceId(serviceToUpdate.get().getServiceId())
                    .serviceName(changeServiceRequest.getServiceName())
                    .description(changeServiceRequest.getDescription())
                    .price(changeServiceRequest.getPrice())
                    .timeRequired(changeServiceRequest.getTimeRequired())
                    .business(business)
                    .subCategory(subCategory)
                    .imageURL(changeServiceRequest.getImage())
                    .build();
            return iServiceRepository.save(serviceUpdated);
        }
    }

    @Override
    public Boolean deleteService(Long serviceID) {
        try {
            iServiceRepository.deleteById(serviceID);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
