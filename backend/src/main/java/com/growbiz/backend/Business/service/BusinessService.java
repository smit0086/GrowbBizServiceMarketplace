package com.growbiz.backend.Business.service;

import com.growbiz.backend.Business.model.Business;
import com.growbiz.backend.Business.model.BusinessRequest;
import com.growbiz.backend.Business.model.BusinessStatus;
import com.growbiz.backend.Business.repository.IBusinessRepository;
import com.growbiz.backend.Categories.service.Super.ICategoryService;
import com.growbiz.backend.Exception.exceptions.BusinessAlreadyExistsException;
import com.growbiz.backend.Exception.exceptions.BusinessNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BusinessService implements IBusinessService {

    @Autowired
    private IFileStorageService fileStorageService;
    @Autowired
    private IBusinessRepository businessRepository;
    @Autowired
    private IBusinessHourService businessHourService;

    @Autowired
    private ICategoryService categoryService;

    @Override
    public List<Business> fetchBusinesses(String status) {
        if (Objects.isNull(status)) {
            return fetchAllBusinesses();
        }
        return businessRepository.findByStatusEquals(BusinessStatus.valueOf(status));
    }

    @Override
    public List<Business> fetchAllBusinesses() {
        return StreamSupport.stream(businessRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Business findByEmail(String email) {
        Business business = businessRepository.findByEmail(email);
        if (Objects.isNull(business)) {
            throw new BusinessNotFoundException("There is no business linked to the given email");
        }
        return business;
    }

    @Override
    public Business findById(Long businessId) {
        Optional<Business> businessOptional = businessRepository.findById(businessId);
        if (businessOptional.isPresent()) {
            return businessOptional.get();
        }
        throw new UsernameNotFoundException("User doesn't exists");
    }

    @Override
    public void save(Business business) {
        businessRepository.save(business);
    }

    @Override
    public Business save(BusinessRequest businessRequest) {
        if (Objects.nonNull(businessRepository.findByEmail(businessRequest.getEmail()))) {
            throw new BusinessAlreadyExistsException("Business already exists with the given email");
        }
        String fileURL = fileStorageService.uploadFileToStorage(businessRequest.getFile(), businessRequest.getEmail());
        Business business = Business.builder()
                .businessName(businessRequest.getBusinessName())
                .email(businessRequest.getEmail())
                .fileURL(fileURL)
                .status(BusinessStatus.PENDING)
                .category(categoryService.getCategoryByID(businessRequest.getCategoryId()))
                .description(businessRequest.getDescription())
                .build();
        Business savedBusiness = businessRepository.save(business);
        businessHourService.init(savedBusiness.getBusinessId());
        return business;
    }

    @Override
    public Business updateBusiness(BusinessRequest businessRequest, Long businessId) {
        String fileURL = fileStorageService.uploadFileToStorage(businessRequest.getFile(), businessRequest.getEmail());
        Business business = Business.builder()
                .businessId(businessId)
                .businessName(businessRequest.getBusinessName())
                .email(businessRequest.getEmail())
                .fileURL(fileURL)
                .status(BusinessStatus.PENDING)
                .category(categoryService.getCategoryByID(businessRequest.getCategoryId()))
                .description(businessRequest.getDescription())
                .build();
        businessRepository.save(business);
        return business;
    }
}
