package com.growbiz.backend.Services.service;

import com.growbiz.backend.Business.model.Business;
import com.growbiz.backend.Business.service.IBusinessService;
import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Categories.service.Sub.ISubCategoryService;
import com.growbiz.backend.Categories.service.Super.ICategoryService;
import com.growbiz.backend.File.service.IFileStorageService;
import com.growbiz.backend.Services.models.ServiceRequest;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.Services.repository.IServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private final IFileStorageService fileStorageService;

    @Override
    public Services getServiceById(Long serviceId) {
        try {
            Optional<Services> service = iServiceRepository.findById(serviceId);
            return service.orElse(null);
        } catch (Exception e) {
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

        if (Objects.nonNull(existingService)) {
            return null;
        } else {
            Business business = businessService.findById(newServiceRequest.getBusinessID());
            SubCategory subCategory = subCategoryService.getSubCategoryByID(newServiceRequest.getSubCategoryID());
            String imageUrl = fileStorageService.uploadFileToStorage(newServiceRequest.getImage(), newServiceRequest.getEmail());
            Services service = Services.builder()
                    .serviceName(newServiceRequest.getServiceName())
                    .description(newServiceRequest.getDescription())
                    .price(newServiceRequest.getPrice())
                    .timeRequired(newServiceRequest.getTimeRequired())
                    .business(business)
                    .subCategory(subCategory)
                    .imageURL(imageUrl)
                    .build();

            return iServiceRepository.save(service);
        }
    }

    @Override
    public Services updateService(ServiceRequest changeServiceRequest) {
        Services serviceToUpdate = iServiceRepository.findById(changeServiceRequest.getServiceID()).get();

        if (Objects.isNull(serviceToUpdate)) {
            return null;
        } else {
            Business business = businessService.findById(changeServiceRequest.getBusinessID());
            SubCategory subCategory = subCategoryService.getSubCategoryByID(changeServiceRequest.getSubCategoryID());
            String imageUrl = fileStorageService.uploadFileToStorage(changeServiceRequest.getImage(), changeServiceRequest.getEmail());

            Services serviceUpdated = Services.builder()
                    .serviceId(serviceToUpdate.getServiceId())
                    .serviceName(changeServiceRequest.getServiceName())
                    .description(changeServiceRequest.getDescription())
                    .price(changeServiceRequest.getPrice())
                    .timeRequired(changeServiceRequest.getTimeRequired())
                    .business(business)
                    .subCategory(subCategory)
                    .imageURL(imageUrl)
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
