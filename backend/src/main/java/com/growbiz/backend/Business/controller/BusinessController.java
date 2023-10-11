package com.growbiz.backend.Business.controller;

import com.growbiz.backend.Business.helper.BusinessControllerHelper;
import com.growbiz.backend.Business.model.BusinessRequest;
import com.growbiz.backend.Business.model.BusinessResponse;
import com.growbiz.backend.Business.model.BusinessStatus;
import com.growbiz.backend.Business.service.IBusinessService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    private IBusinessService businessService;

    @Autowired
    private BusinessControllerHelper helper;

    @GetMapping(path = "/all")
    public ResponseEntity<BusinessResponse> getAllBusiness(@RequestParam(required = false) String status) {

        if (BusinessStatus.PENDING.name().equalsIgnoreCase(status)) {
            return helper.createBusinessResponse(businessService.fetchAllPendingBusinesses());
        }
        return helper.createBusinessResponse(businessService.fetchAllBusinesses());
    }

    @GetMapping(path = "/")
    public ResponseEntity<BusinessResponse> getBusiness(@RequestBody BusinessRequest businessRequest) {
        return helper.createBusinessResponse(List.of(businessService.findByEmail(businessRequest.getEmail())));
    }

    @RolesAllowed("PARTNER")
    @RequestMapping(path = "/save", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BusinessResponse> saveBusiness(@RequestPart("business") String businessRequestJson, @RequestPart("file") MultipartFile file) {
        return helper.createBusinessResponse(List.of(businessService.save(helper.getBusinessRequestFromJSON(businessRequestJson, file))));
    }
}


