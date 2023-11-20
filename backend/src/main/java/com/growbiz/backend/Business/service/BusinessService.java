package com.growbiz.backend.Business.service;

import com.growbiz.backend.Business.helper.BusinessServiceHelper;
import com.growbiz.backend.Business.models.Business;
import com.growbiz.backend.Business.repository.IBusinessRepository;
import com.growbiz.backend.BusinessHour.service.IBusinessHourService;
import com.growbiz.backend.Categories.service.Super.ICategoryService;
import com.growbiz.backend.Enums.BusinessStatus;
import com.growbiz.backend.Exception.exceptions.Business.BusinessAlreadyExistsException;
import com.growbiz.backend.Exception.exceptions.Business.BusinessNotFoundException;
import com.growbiz.backend.Responses.Business.BusinessRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BusinessService implements IBusinessService {

    @Autowired
    private IBusinessRepository businessRepository;
    @Autowired
    private IBusinessHourService businessHourService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private BusinessServiceHelper helper;

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
        String fileURL = helper.uploadAndGetFileURL(businessRequest);
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
        return savedBusiness;
    }

    @Override
    public Business updateBusiness(BusinessRequest businessRequest, Long businessId) {
        String fileURL = helper.uploadAndGetFileURL(businessRequest);
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

    @Override
    public byte[] downloadFile(String email) {
        try {
            Business business = findByEmail(email);
            String folderPath = business.getFileURL();
            List<File> files = Files.list(Paths.get(folderPath.substring(0, folderPath.lastIndexOf("/"))))
                    .map(Path::toFile)
                    .filter(File::isFile)
                    .toList();
            return Files.readAllBytes(files.get(0).toPath());
        } catch (IOException ioException) {
            System.out.println("Error in FileStorageService.downloadFile " + ioException);
        }
        return new byte[0];
    }
}
