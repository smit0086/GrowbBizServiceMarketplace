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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @RequestMapping(path = "/addService",
            method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ServiceResponse> addService(@RequestPart("service") String serviceRequestJSON, @RequestPart("image") MultipartFile image) {
        ServiceRequest newService = serviceHelper.getServiceRequestFromJSON(serviceRequestJSON, image);
        Services service = servicesService.addService(newService);

        if (service != null) {
            return serviceHelper.createServiceResponse(List.of(service), false);
        } else {
             throw new ServiceAlreadyExistsException("The requested new service to add, already exists!");
        }
    }

    @RequestMapping(path = "/updateService",
            method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ServiceResponse> updateService(@RequestPart("service") String serviceRequestJSON, @RequestPart("image") MultipartFile image) {
        ServiceRequest newService = serviceHelper.getServiceRequestFromJSON(serviceRequestJSON, image);
        Services updateService = servicesService.updateService(newService);

        if (updateService != null) {
            return serviceHelper.createServiceResponse(List.of(updateService), true);
        } else {
            throw new ServiceNotFoundException("The specified service for update in not found");
        }
    }

    @DeleteMapping(path = "/deleteService")
    public ResponseEntity<ServiceResponse> deleteService(@RequestBody Services oldService) {
        Boolean isDeleted = servicesService.deleteService(oldService.getServiceId());

        if (isDeleted) {
            return serviceHelper.deleteServiceResponse(true);
        } else {
            throw new ServiceNotFoundException("The specified service for deletion in not found");
        }
    }

    @GetMapping(path = "/getService")
    public ResponseEntity<ServiceResponse> getService(@RequestParam Long serviceId) {
        Services service = servicesService.getServiceById(serviceId);

        if (service != null) {
            String tax = servicesService.getTaxForService(service);
            return serviceHelper.createServiceResponseWithTax(List.of(service), tax);
        } else {
            throw new ServiceNotFoundException("The specified service is not found");
        }
    }
}
