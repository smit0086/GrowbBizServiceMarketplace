package com.growbiz.backend.Business.helper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.growbiz.backend.Business.models.Business;
import com.growbiz.backend.BusinessHour.model.BusinessHour;
import com.growbiz.backend.Enums.BusinessStatus;
import com.growbiz.backend.RequestResponse.Business.BusinessRequest;
import com.growbiz.backend.RequestResponse.Business.BusinessResponse;
import com.growbiz.backend.RequestResponse.Business.VerificationRequest;
import com.growbiz.backend.RequestResponse.BusinessHour.BusinessHourResponse;
import com.growbiz.backend.Security.service.JWTService;
import com.growbiz.backend.User.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
public class BusinessControllerHelper {

    @Autowired
    private JWTService jwtService;
    private static final String APPROVAL_MESSAGE = "We are delighted to inform you that your business application has been approved! Congratulations! We are excited to have you on board, and we look forward to a fruitful and successful partnership.";
    private static final String DECLINED_MESSAGE = "We regret to inform you that your business application with GrowBiz has been reviewed, and, unfortunately, we are unable to approve it at this time. We encourage you to take a moment to review the reasons for the rejection provided.";


    public BusinessRequest getBusinessRequestFromJSON(String json, MultipartFile file) {
        BusinessRequest businessRequest = new BusinessRequest();
        try {
            ObjectMapper objectMapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            businessRequest = objectMapper.readValue(json, BusinessRequest.class);
        } catch (IOException err) {
            System.out.println(err);
        }
        businessRequest.setFile(file);
        return businessRequest;
    }

    public ResponseEntity<BusinessResponse> createBusinessResponse(List<Business> businessList) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(BusinessResponse.builder()
                .businesses(businessList)
                .subject(user.getEmail())
                .role(user.getRole())
                .build());
    }

    public String getEmailBody(VerificationRequest verificationRequest) {
        if (BusinessStatus.APPROVED.equals(verificationRequest.getStatus())) {
            return APPROVAL_MESSAGE;
        }
        return DECLINED_MESSAGE;
    }

    public ResponseEntity<BusinessHourResponse> createBusinessHourResponse(BusinessHour businessHour) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(BusinessHourResponse.builder().businessHour(businessHour).subject(user.getEmail())
                .role(user.getRole())
                .build());
    }
}
