package com.growbiz.backend.UserAuthentication.model;

import com.growbiz.backend.Responses.Basic.BasicResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AuthenticationResponse extends BasicResponse {
    private String token;

}