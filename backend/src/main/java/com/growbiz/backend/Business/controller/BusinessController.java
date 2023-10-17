package com.growbiz.backend.Business.controller;

import com.growbiz.backend.Business.helper.BusinessControllerHelper;
import com.growbiz.backend.Business.model.Business;
import com.growbiz.backend.Business.model.BusinessResponse;
import com.growbiz.backend.Business.model.VerificationRequest;
import com.growbiz.backend.Business.service.IBusinessService;
import com.growbiz.backend.Business.service.IFileStorageService;
import com.growbiz.backend.Business.service.ISendEmailService;
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
    private IFileStorageService fileStorageService;

    @Autowired
    private ISendEmailService sendEmailService;

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

    @RequestMapping(path = "/update/{businessId}", method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BusinessResponse> updateBusiness(@RequestPart("business") String businessRequestJson, @RequestPart("file") MultipartFile file, @PathVariable("businessId") Long businessId) {
        return helper.createBusinessResponse(List.of(businessService.updateBusiness(helper.getBusinessRequestFromJSON(businessRequestJson, file), businessId)));
    }

    @PutMapping(path = "/{businessId}/verify")
    public ResponseEntity<String> verifyBusiness(@PathVariable("businessId") Long businessId, @RequestBody VerificationRequest verificationRequest) {
        Business business = businessService.findById(businessId);
        business.setStatus(verificationRequest.getStatus());
        business.setReason(verificationRequest.getReason());
        businessService.save(business);
        sendEmailService.sendEmail(business.getEmail(), "Your Business has been " + verificationRequest.getStatus(), helper.getEmailBody(verificationRequest));
        return ResponseEntity.ok("Business " + business.getBusinessName() + " has been " + verificationRequest.getStatus() + "! Email has been sent to the Partner");
    }

    @GetMapping(path = "/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam String email) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(fileStorageService.downloadFile(email));
    }

}


