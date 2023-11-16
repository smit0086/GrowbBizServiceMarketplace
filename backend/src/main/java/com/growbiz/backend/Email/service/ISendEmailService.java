package com.growbiz.backend.Email.service;

public interface ISendEmailService {
    public void sendEmail(String to, String subject, String body);

}
