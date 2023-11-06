package com.growbiz.backend.Services.service;

import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.Services.models.ServiceRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IServicesService {
    Services getServiceById(Long serviceId);

    List<Services> getServiceByBusinessId(Long businessId);

    List<Services> getServiceBySubCategoryId(Long subCategoryId);

    List<Services> fetchServiceList();

    Services addService(ServiceRequest newService);

    Services updateService(ServiceRequest service);

    Boolean deleteService(Long serviceID);
}
