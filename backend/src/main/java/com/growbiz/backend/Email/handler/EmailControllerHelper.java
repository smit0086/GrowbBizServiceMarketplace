package com.growbiz.backend.Email.handler;

import com.growbiz.backend.Email.model.EmailResponse;
import org.springframework.stereotype.Component;


@Component
public class EmailControllerHelper {

    public String generateHeadSection(EmailResponse emailResponse) {
        String headSection = "Dear Customer,\n \n" +
                "We would like to confirm your upcoming service appointment with " + emailResponse.getServiceName() + " at "
                + emailResponse.getBusinessName() + ".";
        return headSection;
    }

    public String generateMessageBody(EmailResponse emailResponse) {
        String body = "Here are the details of your appointment:\n"
                + "Service Name: " + emailResponse.getServiceName()+ "\nDate: " + emailResponse.getDate() + "Time:" + emailResponse.getTime() + "\n"
                + "If you have any questions or need to adjust your appointment,"
                + "please don't hesitate to reach out to us.\n";
        return body;
    }

    public String generateConcludingSection(EmailResponse emailResponse) {
        String concludingSection = "Thank you once again for choosing " + emailResponse.getBusinessName()
                + "!\n \nBest Regards,\n"
                + emailResponse.getUser() + "\n" + "Charlie Mace\n" + emailResponse.getBusinessName() + "\n" +  emailResponse.getFrom();
        return concludingSection;
    }
}
