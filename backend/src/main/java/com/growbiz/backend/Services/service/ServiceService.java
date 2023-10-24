package com.growbiz.backend.Services.service;

import com.growbiz.backend.Booking.service.IBookingService;
import com.growbiz.backend.Categories.service.Sub.ISubCategoryService;
import com.growbiz.backend.Services.repository.IServiceRepository;
import com.growbiz.backend.Services.service.IServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceService implements IServiceService {

    @Autowired
    private final IServiceRepository serviceRepository;

    @Autowired
    private final ISubCategoryService subCategoryService;

    @Autowired
    private final IBookingService bookingService;



}
