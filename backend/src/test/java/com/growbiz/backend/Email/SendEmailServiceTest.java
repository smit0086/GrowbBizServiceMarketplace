package com.growbiz.backend.Email;

import com.growbiz.backend.Email.model.EmailRequest;
import com.growbiz.backend.Email.service.SendEmailService;
import com.growbiz.backend.TestConstants.TestConstants;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.Locale;

@ExtendWith(MockitoExtension.class)
public class SendEmailServiceTest {
    @InjectMocks
    private SendEmailService sendEmailServiceMock;
    @Mock
    private JavaMailSender mailSenderMock;
    @Mock
    private TemplateEngine templateEngine;
//    @Mock
    EmailRequest emailRequest;

    @BeforeEach
    public void init() {
        doNothing().when(mailSenderMock).send(any(SimpleMailMessage.class));
    }

    @Test
    public void testSendEmail() {
        sendEmailServiceMock.sendEmail(TestConstants.TEST_EMAIL, "TestSubject", "TestBody");
    }

    @Test
    public void testSendEmailWithHtmlTemplate() {
        emailRequest = EmailRequest.builder()
                .businessName(TestConstants.TEST_BUSINESS_NAME)
                .serviceName(TestConstants.TEST_SERVICE_NAME)
                .user("John Doe")
                .to(TestConstants.TEST_EMAIL)
                .subject("Subject: Confirmation of Your Upcoming Service Appointment")
                .time(TestConstants.TEST_BOOKING_START_TIME)
                .date(TestConstants.TEST_BOOKING_DATE).build();

        MimeMessage mimeMessage = mock(MimeMessage.class);

        Locale locale = mock(Locale.class);
        when(mailSenderMock.createMimeMessage()).thenReturn(mimeMessage);

        when(templateEngine.process(eq("emailTemplate"), any(Context.class)))
                .thenReturn("Mocked HTML Content");

        sendEmailServiceMock.sendEmailWithHtmlTemplate(emailRequest, "emailTemplate", locale);

        verify(mailSenderMock).createMimeMessage();
        verify(templateEngine).process(eq("emailTemplate"), any(Context.class));
    }
}
