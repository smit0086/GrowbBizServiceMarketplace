package com.growbiz.backend.Business.controller;

import com.growbiz.backend.Business.helper.BusinessControllerHelper;
import com.growbiz.backend.Business.model.Business;
import com.growbiz.backend.Business.model.BusinessResponse;
import com.growbiz.backend.Business.model.BusinessStatus;
import com.growbiz.backend.Business.service.IBusinessService;
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
    public ResponseEntity<BusinessResponse> getAllBusinesses(@RequestParam(required = false) String status) {
        return helper.createBusinessResponse(businessService.fetchBusinesses(status));
    }

    @GetMapping(path = "/")
    public ResponseEntity<BusinessResponse> getBusiness(@RequestParam String email) {
        return helper.createBusinessResponse(List.of(businessService.findByEmail(email)));
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BusinessResponse> saveBusiness(@RequestPart("business") String businessRequestJson, @RequestPart("file") MultipartFile file) {
        return helper.createBusinessResponse(List.of(businessService.save(helper.getBusinessRequestFromJSON(businessRequestJson, file))));
    }

    @RequestMapping(path = "/{businessId}", method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BusinessResponse> updateBusiness(@RequestPart("business") String businessRequestJson, @RequestPart("file") MultipartFile file, @PathVariable("businessId") Long businessId) {
        return helper.createBusinessResponse(List.of(businessService.updateBusiness(helper.getBusinessRequestFromJSON(businessRequestJson, file), businessId)));
    }

    @PutMapping(path = "/{businessId}/verify")
    public ResponseEntity<String> verifyBusiness(@PathVariable("businessId") Long businessId) {
        Business business = businessService.findById(businessId);
        business.setStatus(BusinessStatus.APPROVED);
        businessService.save(business);
        return ResponseEntity.ok("Success");
    }

}


