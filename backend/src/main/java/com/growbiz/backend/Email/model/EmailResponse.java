package com.growbiz.backend.Email.model;

import com.growbiz.backend.Responses.model.BasicResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.mail.SimpleMailMessage;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class EmailResponse extends BasicResponse {
    private SimpleMailMessage mailMessage;
    private String status;
    private String email;
    private LocalTime time;
    private String date;
    private String serviceName;
    private String businessName;
    private String from;
    private String user;
}
