package com.growbiz.backend.Email.controller;

import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Booking.service.IBookingService;
import com.growbiz.backend.Email.model.EmailRequest;
import com.growbiz.backend.Email.service.ISendEmailService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private final ISendEmailService sendEmailService;

    @Autowired
    private final IBookingService iBookingService;

    @PostMapping(path="/sendEmailReminder")
    public ResponseEntity sendEmailReminder(@RequestBody long bookingId) {

        Booking booking = iBookingService.findById(bookingId);

        String subject = "Subject: Confirmation of Your Upcoming Service Appointment";

        EmailRequest emailResponse = EmailRequest.builder()
                .businessName(booking.getService().getBusiness().getBusinessName())
                .serviceName(booking.getService().getServiceName())
                .user(booking.getUser().getFirstName() + " " + booking.getUser().getLastName())
                .time(booking.getStartTime())
                .date(booking.getDate())
                .to(booking.getUser().getEmail())
                .subject(subject)
                .build();

        Locale locale = Locale.getDefault();

        sendEmailService.sendEmailWithHtmlTemplate(emailResponse, "emailTemplate.html", locale);

        return ResponseEntity.ok("Email has been sent to the given email " + booking.getUser().getEmail());
    }
}
