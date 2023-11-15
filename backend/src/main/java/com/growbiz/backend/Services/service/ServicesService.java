package com.growbiz.backend.Services.service;

import com.growbiz.backend.Business.model.Business;
import com.growbiz.backend.Business.service.IBusinessService;
import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Categories.service.Sub.ISubCategoryService;
import com.growbiz.backend.Categories.service.Super.ICategoryService;
import com.growbiz.backend.Exception.exceptions.ServiceAlreadyExistsException;
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
    public Services addService(ServiceRequest newService) {
        try {
            boolean checkNameAndBusiness =
                    Objects.nonNull(iServiceRepository.findByServiceNameAndBusinessBusinessId(
                            newService.getServiceName(),
                            newService.getBusinessID())
                    );

            if (!checkNameAndBusiness) {
                Business business = businessService.findById(newService.getBusinessID());
                SubCategory subCategory = subCategoryService.getSubCategoryByID(newService.getSubCategoryID());
                String imageUrl = fileStorageService.uploadFileToStorage(newService.getImage(), newService.getEmail());
                Services service = Services.builder()
                        .serviceName(newService.getServiceName())
                        .description(newService.getDescription())
                        .price(newService.getPrice())
                        .timeRequired(newService.getTimeRequired())
                        .business(business)
                        .subCategory(subCategory)
                        .imageURL(imageUrl)
                        .build();
                return iServiceRepository.save(service);
            } else {
                throw new ServiceAlreadyExistsException("Service already Exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
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

            if (Objects.nonNull(changedService.getDescription())) {
                serviceUpdated.setDescription(changedService.getDescription());
            }

            if (Objects.nonNull(changedService.getPrice())) {
                serviceUpdated.setPrice(changedService.getPrice());
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
