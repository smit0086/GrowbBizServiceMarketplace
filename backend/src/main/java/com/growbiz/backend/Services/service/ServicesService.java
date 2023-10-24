package com.growbiz.backend.Services.service;

import com.growbiz.backend.Business.model.Business;
import com.growbiz.backend.Business.service.IBusinessService;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.Categories.service.Sub.ISubCategoryService;
import com.growbiz.backend.Services.models.ServiceRequest;
import com.growbiz.backend.Services.repository.IServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            return (List<Services>) iServiceRepository.findByBusinessId(businessId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Services> getServiceBySubCategoryId(Long subCategoryId) {
        try {
            return (List<Services>) iServiceRepository.findBySubCategoryId(subCategoryId);
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
    public Services addService(Services newService) {
        try {
            return iServiceRepository.save(newService);
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public Services updateService(ServiceRequest service, Long serviceID) {
        try {
            Business business = businessService.findById(service.getBusinessID());
            SubCategory subCategory = subCategoryService.getSubCategoryByID(service.getSubCategoryID());
            Services serviceUpdated =  Services.builder()
                    .serviceId(serviceID)
                    .serviceName(service.getServiceName())
                    .description(service.getDescription())
                    .timeRequired(service.getTimeRequired())
                    .business(business)
                    .subCategory(subCategory)
                    .build();
            return iServiceRepository.save(serviceUpdated);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteService(Long serviceID) {
        try {
            iServiceRepository.deleteById(serviceID);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
