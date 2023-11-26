package com.growbiz.backend.Email.service;

import com.growbiz.backend.Email.model.EmailRequest;

import java.util.Locale;

public interface ISendEmailService {
    public void sendEmail(String to, String subject, String body);

    public void sendEmailWithHtmlTemplate(EmailRequest emailResponse, String templateName, Locale locale);
}
