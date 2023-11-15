package com.growbiz.backend.Email;

import com.growbiz.backend.Email.service.SendEmailService;
import com.growbiz.backend.TestConstants.TestConstants;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class SendEmailServiceTest {
    @InjectMocks
    private SendEmailService sendEmailServiceMock;

    @Mock
    private JavaMailSender mailSenderMock;

    @Before
    public void init() {
        doNothing().when(mailSenderMock).send(any(SimpleMailMessage.class));
    }

    @Test
    public void testSendEmail() {
        sendEmailServiceMock.sendEmail(TestConstants.TEST_EMAIL, "TestSubject", "TestBody");
    }
}
