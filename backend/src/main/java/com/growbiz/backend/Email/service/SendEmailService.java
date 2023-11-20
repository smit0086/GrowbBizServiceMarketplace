package com.growbiz.backend.Email.service;

import com.growbiz.backend.Email.model.EmailRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;

@Service
public class SendEmailService implements ISendEmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    @Override
    public void sendEmailWithHtmlTemplate(EmailRequest emailResponse, String templateName, Locale locale) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, "UTF-8");

        final Context context = new Context(locale);
        context.setVariable("customer name", emailResponse.getUser());
        context.setVariable("date", emailResponse.getDate());
        context.setVariable("time", emailResponse.getTime());
        context.setVariable("business", emailResponse.getBusinessName());
        context.setVariable("service", emailResponse.getServiceName());

        try {
            messageHelper.setTo(emailResponse.getTo());
            messageHelper.setSubject(emailResponse.getSubject());
            String htmlContent = templateEngine.process(templateName, context);
            messageHelper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            // Handle exception
            e.printStackTrace();
            e.getMessage();
        }
    }
}
