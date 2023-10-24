package com.growbiz.backend.Services.controller;

import com.growbiz.backend.Business.model.Business;
import com.growbiz.backend.Categories.helper.CategoriesControllerHelper;
import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Exception.exceptions.CategoryAlreadyExistsException;
import com.growbiz.backend.Exception.exceptions.ServiceAlreadyExistsException;
import com.growbiz.backend.Exception.exceptions.ServiceNotFoundException;
import com.growbiz.backend.Services.helper.ServicesControllerHelper;
import com.growbiz.backend.Services.models.ServiceRequest;
import com.growbiz.backend.Services.models.ServiceResponse;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.Services.service.IServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/service")
public class ServiceController {
    @Autowired
    private IServicesService servicesService;

    @Autowired
    private CategoriesControllerHelper categoryHelper;

    @Autowired
    private ServicesControllerHelper serviceHelper;

    @GetMapping(path = "/allServices")
    public ResponseEntity<ServiceResponse> getAllServices() {
        return serviceHelper.createServiceResponse(servicesService.fetchServiceList());
    }

    // Questions to ask
    @GetMapping(path = "/allServicesByBusinessId")
    public ResponseEntity<ServiceResponse> getAllServicesByBusinessId(@RequestBody Business business) {
        return serviceHelper.createServiceResponse(servicesService.getServiceByBusinessId(business.getBusinessId()));
    }

    @PostMapping(path = "/addService")
    public ResponseEntity<ServiceResponse> addService(@RequestBody Services newService) {
        Services service = servicesService.addService(newService);

        if (service != null) {
            return serviceHelper.createServiceResponse(List.of(service));
        } else {
            throw new ServiceAlreadyExistsException("The requested new service to add, already exists!");
        }
    }

    @PutMapping(path = "/updateService")
    public ResponseEntity<ServiceResponse> updateService(@RequestBody ServiceRequest newService) throws Exception {
        Services updateService = servicesService.updateService(newService, newService.getServiceID());

        if (updateService != null) {
            return serviceHelper.createServiceResponse(List.of(updateService));
        } else {
            throw new ServiceNotFoundException("The specified service for update in not found");
        }
    }

    @DeleteMapping(path = "/deleteCategory")
    public ResponseEntity<ServiceResponse> deleteService(@RequestBody Services oldService) throws Exception {
        Boolean isDeleted = servicesService.deleteService(oldService.getServiceId());
        if (isDeleted) {
            return serviceHelper.deleteServiceResponse(true);
        } else {
            throw new ServiceNotFoundException("The specified service for deletion in not found");
        }
    }
}
