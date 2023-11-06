package com.growbiz.backend.Services.controller;

import com.growbiz.backend.Categories.helper.CategoryControllerHelper;
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
@RequestMapping("/services")
public class ServiceController {
    @Autowired
    private IServicesService servicesService;

    @Autowired
    private CategoryControllerHelper categoryHelper;

    @Autowired
    private ServicesControllerHelper serviceHelper;

    @GetMapping(path = "/allServices")
    public ResponseEntity<ServiceResponse> getAllServices() {
        return serviceHelper.createServiceResponse(servicesService.fetchServiceList(), false);
    }

    @GetMapping(path = "/allServicesByBusinessId")
    public ResponseEntity<ServiceResponse> getAllServicesByBusinessId(@RequestParam Long businessID) {
        return serviceHelper.createServiceResponse(servicesService.getServiceByBusinessId(businessID), false);
    }

    @GetMapping(path = "/allServicesBySubCategoryId")
    public ResponseEntity<ServiceResponse> getAllServicesBySubCategoryId(@RequestParam Long subCategoryID) {
        return serviceHelper.createServiceResponse(servicesService.getServiceBySubCategoryId(subCategoryID), false);
    }

    @PostMapping(path = "/addService")
    public ResponseEntity<ServiceResponse> addService(@RequestBody ServiceRequest newService) {
        Services service = servicesService.addService(newService);
        if (service != null) {
            return serviceHelper.createServiceResponse(List.of(service), false);
        } else {
            throw new ServiceAlreadyExistsException("The requested new service to add, already exists!");
        }
    }

    @PutMapping(path = "/updateService")
    public ResponseEntity<ServiceResponse> updateService(@RequestBody ServiceRequest newService) throws Exception {
        Services updateService = servicesService.updateService(newService);

        if (updateService != null) {
            return serviceHelper.createServiceResponse(List.of(updateService), true);
        } else {
            throw new ServiceNotFoundException("The specified service for update in not found");
        }
    }

    @DeleteMapping(path = "/deleteService")
    public ResponseEntity<ServiceResponse> deleteService(@RequestBody Services oldService) throws Exception {
        Boolean isDeleted = servicesService.deleteService(oldService.getServiceId());
        if (isDeleted) {
            return serviceHelper.deleteServiceResponse(true);
        } else {
            throw new ServiceNotFoundException("The specified service for deletion in not found");
        }
    }
}
