package com.growbiz.backend.Business.service;

import com.growbiz.backend.Business.model.Business;
import com.growbiz.backend.Business.model.BusinessRequest;
import com.growbiz.backend.Business.model.BusinessStatus;
import com.growbiz.backend.Business.repository.IBusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BusinessService implements IBusinessService {

    @Autowired
    private IFileStorageService fileStorageService;
    @Autowired
    private IBusinessRepository businessRepository;

    @Override
    public List<Business> fetchAllBusinesses() {
        return StreamSupport.stream(businessRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Business findByEmail(String email) {
        return businessRepository.findByEmail(email);
    }

    @Override
    public List<Business> fetchAllPendingBusinesses() {
        return businessRepository.findByStatusEquals(BusinessStatus.PENDING);
    }

    @Override
    public Business save(BusinessRequest businessRequest) {
        String fileURL = fileStorageService.uploadFileToStorage(businessRequest.getFile(), businessRequest.getBusinessName());
        Business business = Business.builder()
                .businessName(businessRequest.getBusinessName())
                .email(businessRequest.getEmail())
                .fileURL(fileURL)
                .status(BusinessStatus.PENDING)
                .categoryId(businessRequest.getCategoryId())
                .build();
        businessRepository.save(business);
        return business;
    }
}
