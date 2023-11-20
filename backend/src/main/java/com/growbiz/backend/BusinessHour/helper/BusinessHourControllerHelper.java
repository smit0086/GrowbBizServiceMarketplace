package com.growbiz.backend.BusinessHour.helper;

import com.growbiz.backend.BusinessHour.model.BusinessHour;
import com.growbiz.backend.Responses.BusinessHour.BusinessHourResponse;
import com.growbiz.backend.User.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class BusinessHourControllerHelper {
    public ResponseEntity<BusinessHourResponse> createBusinessHourResponse(BusinessHour businessHour) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(BusinessHourResponse.builder().businessHour(businessHour).subject(user.getEmail())
                .role(user.getRole())
                .build());
    }
}
