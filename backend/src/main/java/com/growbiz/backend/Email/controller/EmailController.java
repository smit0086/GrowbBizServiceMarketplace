package com.growbiz.backend.Email.controller;

import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Booking.service.IBookingService;
import com.growbiz.backend.Business.model.Business;
import com.growbiz.backend.Business.service.IBusinessService;
import com.growbiz.backend.Email.handler.EmailControllerHelper;
import com.growbiz.backend.Email.model.EmailResponse;
import com.growbiz.backend.Email.service.ISendEmailService;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.Services.service.IServicesService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity sendEmailReminder(@RequestBody long bookingId) {

        Booking booking = iBookingService.findById(bookingId);
        Services service = iServicesService.getServiceById(booking.getService().getServiceId());
        Business business = iBusinessService.findById(service.getBusiness().getBusinessId());

        String subject = "Subject: Confirmation of Your Upcoming Service Appointment";

        EmailResponse emailResponse = EmailResponse.builder()
                .businessName(business.getBusinessName())
                .serviceName(service.getServiceName())
                .user(booking.getUser().getFirstName() + " " + booking.getUser().getLastName())
                .time(booking.getStartTime())
                .date(booking.getDate()).build();

        String opening = emailControllerHelper.generateHeadSection();
        String body = emailControllerHelper.generateMessageBody(emailResponse);
        String conclusion = emailControllerHelper.generateEndSection(emailResponse);

        String mailContent = opening + body + conclusion;

        sendEmailService.sendEmail(booking.getUser().getEmail(), subject, mailContent);

        return ResponseEntity.ok("Email has been sent to the given email " + booking.getUser().getEmail());
    }
}
