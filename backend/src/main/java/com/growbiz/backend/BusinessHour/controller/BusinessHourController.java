package com.growbiz.backend.BusinessHour.controller;

import com.growbiz.backend.BusinessHour.model.BusinessHour;
import com.growbiz.backend.BusinessHour.service.IBusinessHourService;
import com.growbiz.backend.RequestResponse.BusinessHour.BusinessHourRequest;
import com.growbiz.backend.RequestResponse.BusinessHour.BusinessHourResponse;
import com.growbiz.backend.User.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/businessHours")
public class BusinessHourController {

    @Autowired
    IBusinessHourService businessHourService;

    @PutMapping
    public ResponseEntity<String> updateBusinessHour(@RequestBody BusinessHourRequest businessHourRequest) {
        businessHourService.saveBusinessHours(businessHourRequest);
        return ResponseEntity.ok("Updated");
    }

    @GetMapping
    public ResponseEntity<BusinessHourResponse> getBusinessHours(@RequestParam String businessId) {
        Long bId = Long.parseLong(businessId);
        return createBusinessHourResponse(businessHourService.getBusinessHour(bId));
    }

    public ResponseEntity<BusinessHourResponse> createBusinessHourResponse(BusinessHour businessHour) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(BusinessHourResponse.builder().businessHour(businessHour).subject(user.getEmail())
                .role(user.getRole())
                .build());
    }
}
