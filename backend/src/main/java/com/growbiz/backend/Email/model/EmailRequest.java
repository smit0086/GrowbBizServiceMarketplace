package com.growbiz.backend.Email.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;

@Data
@SuperBuilder
public class EmailRequest {

    private String to;
    private String subject;
    private String user;
    private String serviceName;
    private String businessName;
    private LocalTime time;
    private String date;

}
