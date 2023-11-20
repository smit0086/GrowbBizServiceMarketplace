package com.growbiz.backend.Email.handler;

import com.growbiz.backend.Email.model.EmailRequest;
import org.springframework.stereotype.Component;


@Component
public class EmailControllerHelper {

    public String generateHeadSection() {
        String headSection = "Dear Customer,\n" +
                "This is a reminder for your upcoming service appointment\n";
        return headSection;
    }

    public String generateMessageBody(EmailRequest emailResponse) {
        String body = "Details of your appointment:\n"
                + "Service Name: " + emailResponse.getServiceName()
                + "\nDate: " + emailResponse.getDate()
                + "\nTime:" + emailResponse.getTime()
                + "\nIf you have any questions or need to adjust your appointment, reach out to us.\n";
        return body;
    }

    public String generateEndSection(EmailRequest emailResponse) {
        String endSection = "\nBest Regards,\n"
                + emailResponse.getUser()
                + "\nCharlie Mace\n"
                + emailResponse.getBusinessName();
        return endSection;
    }
}
