package com.growbiz.backend.Services.helper;


import com.growbiz.backend.Services.models.ServiceResponse;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.User.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServicesControllerHelper {

    public ResponseEntity<ServiceResponse> createServiceResponse(List<Services> servicesList, Boolean isUpdated) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(ServiceResponse.builder()
                .services(servicesList)
                .isUpdated(isUpdated)
                .subject(user.getEmail())
                .role(user.getRole())
                .build());
    }

    public ResponseEntity<ServiceResponse> deleteServiceResponse(Boolean deleted) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(ServiceResponse.builder()
                .isDeleted(deleted)
                .subject(user.getEmail())
                .role(user.getRole())
                .build());
    }

    public ResponseEntity<ServiceResponse> createServiceResponseWithTax(List<Services> service, String tax) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(ServiceResponse.builder()
                .services(service)
                .subject(user.getEmail())
                .role(user.getRole())
                .tax(tax)
                .businessId(service.get(0).getBusiness().getBusinessId())
                .build());
    }
}

