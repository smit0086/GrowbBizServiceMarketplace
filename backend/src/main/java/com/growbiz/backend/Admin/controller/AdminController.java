package com.growbiz.backend.Admin.controller;

import com.growbiz.backend.Admin.Services.model.ServiceDto;
import com.growbiz.backend.Admin.service.AdminServiceImpl;
import com.growbiz.backend.UserAuthentication.model.AuthenticationResponse;
import com.growbiz.backend.UserAuthentication.service.IUserAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private static Logger logger = LoggerFactory.getLogger(AdminController.class);
    private IUserAuthenticationService authService;
    private AdminServiceImpl adminService;

    @Autowired
    public AdminController(AdminServiceImpl adminService, IUserAuthenticationService authService) {
        this.adminService = adminService;
        this.authService = authService;
    }


    @GetMapping(path = "/getServicesCategories")
    public ResponseEntity<AuthenticationResponse> getAdminService(@RequestBody Long serviceId) {
        try {
            ServiceDto service = new ServiceDto();
            return ResponseEntity.ok().body(new AuthenticationResponse("Accepted", "Service Name: " + service.getServiceName() + "/nService Description: " + service.getDescription()));

        } catch (Exception e) {
            logger.error("Failed to get service categories");
            return ResponseEntity.badRequest().body(new AuthenticationResponse("Denied", "Failed to get service categories" + e.getMessage()));
        }
    }

    @PutMapping(path = "/addServicesCategories")
    public ResponseEntity<AuthenticationResponse> addingAdminServices(@RequestBody ServiceDto newService) {
        try {
            ServiceDto service = new ServiceDto();
            return ResponseEntity.ok().body(new AuthenticationResponse("Accepted", "Service Name: " + service.getServiceName() + "/nService Description: " + service.getDescription()));


        } catch (Exception e) {
            logger.error("Failed to add service categories");
            return ResponseEntity.badRequest().body(new AuthenticationResponse("Denied", "Failed to add service categories" + e.getMessage()));
        }
    }

    @DeleteMapping(path = "/removeServicesCategories")
    public ResponseEntity<AuthenticationResponse> removingAdminServices() {
        try {
            ServiceDto service = new ServiceDto();
            return ResponseEntity.ok().body(new AuthenticationResponse("Accepted", "Service Name: " + service.getServiceName() + "/nService Description: " + service.getDescription()));

        } catch (Exception e) {
            logger.error("Failed to remove service categories");
            return ResponseEntity.badRequest().body(new AuthenticationResponse("Denied", "Failed to get service categories" + e.getMessage()));
        }
    }

}
