package com.growbiz.backend.Exception.models;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class BasicErrorResponse {
    private Integer errorCode;
    private String errorMessage;
    private Date timestamp;
}
