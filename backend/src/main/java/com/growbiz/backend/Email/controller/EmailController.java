package com.growbiz.backend.Email.controller;

import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Booking.repository.IBookingRepository;
import com.growbiz.backend.Booking.service.IBookingService;
import com.growbiz.backend.Business.model.Business;
import com.growbiz.backend.Business.service.IBusinessService;
import com.growbiz.backend.Email.handler.EmailControllerHelper;
import com.growbiz.backend.Email.model.EmailRequest;
import com.growbiz.backend.Email.model.EmailResponse;
import com.growbiz.backend.Email.service.ISendEmailService;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.Services.service.IServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private final ISendEmailService sendEmailService;

    @Autowired
    private final IBookingService iBookingService;

    @Autowired
    private final IServicesService iServicesService;

    @Autowired
    private final IBusinessService iBusinessService;

    @Autowired
    private final EmailControllerHelper emailControllerHelper;

    @PostMapping(path="/sendEmailReminder")
    public ResponseEntity<EmailResponse> sendEmailReminder(EmailRequest emailRequest) {
        try {
            Booking booking = iBookingService.getBookingById(emailRequest.getBookingId());
            Services service = iServicesService.getServiceById(booking.getService().getServiceId());
            Business business = iBusinessService.findById(service.getBusiness().getBusinessId());
            return emailControllerHelper.createSuccessEmailResponse();
        } catch (Exception e) {
            return emailControllerHelper.createFailedEmailResponse();
        }
    }
}
