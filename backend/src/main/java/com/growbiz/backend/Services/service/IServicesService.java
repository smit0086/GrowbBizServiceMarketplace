package com.growbiz.backend.Services.service;

import com.growbiz.backend.RequestResponse.Services.ServiceRequest;
import com.growbiz.backend.Services.models.ServiceDTO;
import com.growbiz.backend.Services.models.Services;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IServicesService {
    Services getServiceById(Long serviceId);

    List<Services> getServiceByBusinessId(Long businessId);

    List<Services> getServiceBySubCategoryId(Long subCategoryId);

    List<ServiceDTO> getServicesByCategoryId(Long categoryId);

    String getTaxForService(Services service);

    List<Services> fetchServiceList();

    Services addService(ServiceRequest newService);

    Services updateService(ServiceRequest service);

    Boolean deleteService(Long serviceID);
}
