package com.growbiz.backend.Business.helper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.growbiz.backend.Business.models.Business;
import com.growbiz.backend.Constants.BusinessConstants;
import com.growbiz.backend.Enums.BusinessStatus;
import com.growbiz.backend.Responses.Business.BusinessRequest;
import com.growbiz.backend.Responses.Business.BusinessResponse;
import com.growbiz.backend.Responses.Business.VerificationRequest;
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

    public BusinessRequest getBusinessRequestFromJSON(String json, MultipartFile file) {
        BusinessRequest businessRequest = new BusinessRequest();
        try {
            ObjectMapper objectMapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            businessRequest = objectMapper.readValue(json, BusinessRequest.class);
        } catch (IOException err) {
            System.out.println(err.toString());
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
            return BusinessConstants.APPROVAL_MESSAGE;
        }
        return BusinessConstants.DECLINED_MESSAGE;
    }
}
