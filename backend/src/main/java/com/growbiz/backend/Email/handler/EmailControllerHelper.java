package com.growbiz.backend.Email.handler;

import com.growbiz.backend.Email.model.EmailRequest;
import com.growbiz.backend.Email.model.EmailResponse;
import com.growbiz.backend.User.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class EmailControllerHelper {

    public ResponseEntity<EmailResponse> createSuccessEmailResponse() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(EmailResponse.builder().build());
    }

    public ResponseEntity<EmailResponse> createFailedEmailResponse() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.badRequest().body(EmailResponse.builder().build());
    }

    public EmailRequest generateMail(String user, String to, String serviceName, LocalTime time, String businessName, String from) {
        String subject = "Subject: Confirmation of Your Upcoming Service Appointment";

        String body =
                "Dear Customer,\n" +
                "We hope this message finds you well" +
                "We would like to confirm your upcoming service appointment with " + serviceName + " at "
                + businessName + "."
                + "We appreciate your trust in us and look forward to providing you with top-notch service.\n"
                + "Here are the details of your appointment:\n"
                + "Service Name: " + serviceName + "\nDate and Time:" + time + "\n"
                + "Our team at" + businessName
                + "is committed to ensuring that your experience with us is seamless and satisfactory."
                + "If you have any questions or need to make any adjustments to your appointment,"
                + "please don't hesitate to reach out to us at " + from + ".\n"
                + "Thank you once again for choosing " + businessName
                + ". We value your business and are dedicated to exceeding your expectations."
                + "Best Regards,\n"
                + user + "\n" + "Charlie Mace\n" + businessName + "\n" + from;

        return EmailRequest.builder().build();
    }
}
