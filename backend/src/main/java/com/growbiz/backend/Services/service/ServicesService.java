package com.growbiz.backend.Services.service;

import com.growbiz.backend.Business.model.Business;
import com.growbiz.backend.Business.service.IBusinessService;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Categories.service.Sub.ISubCategoryService;
import com.growbiz.backend.Services.models.ServiceRequest;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.Services.repository.IServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServicesService implements IServicesService {

    @Autowired
    private final IServiceRepository iServiceRepository;

    @Autowired
    private final ISubCategoryService subCategoryService;

    @Autowired
    private final IBusinessService businessService;

    @Override
    public Services getServiceById(Long serviceId) {
        try {
            Services service = iServiceRepository.findById(serviceId).get();
            return service;
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
    public List<Services> fetchServiceList() {
        try {
            return (List<Services>) iServiceRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Services addService(ServiceRequest newService) {
        try {
            Business business = businessService.findById(newService.getBusinessID());
            SubCategory subCategory = subCategoryService.getSubCategoryByID(newService.getSubCategoryID());
            Services service = Services.builder()
                    .serviceId(newService.getServiceID())
                    .serviceName(newService.getServiceName())
                    .description(newService.getDescription())
                    .timeRequired(newService.getTimeRequired())
                    .business(business)
                    .subCategory(subCategory)
                    .build();

            return iServiceRepository.save(service);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Services updateService(ServiceRequest changedService) {
        try {
            Services serviceUpdated = iServiceRepository.findById(changedService.getServiceID()).get();

            if (Objects.nonNull(changedService.getServiceName()) && !"".equalsIgnoreCase(changedService.getServiceName())) {
                serviceUpdated.setServiceName(changedService.getServiceName());
            }

            if (Objects.nonNull(changedService.getTimeRequired())) {
                serviceUpdated.setTimeRequired(changedService.getTimeRequired());
            }

            if (Objects.nonNull(changedService.getBusinessID())) {
                Business business = businessService.findById(changedService.getBusinessID());
                serviceUpdated.setBusiness(business);
            }

            if (Objects.nonNull(changedService.getSubCategoryID())) {
                SubCategory subCategory = subCategoryService.getSubCategoryByID(changedService.getSubCategoryID());
                serviceUpdated.setSubCategory(subCategory);
            }

            return iServiceRepository.save(serviceUpdated);

        } catch (Exception e) {
            return null;
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
