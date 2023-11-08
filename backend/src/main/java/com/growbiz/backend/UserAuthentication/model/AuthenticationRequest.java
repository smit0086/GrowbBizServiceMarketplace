package com.growbiz.backend.UserAuthentication.model;

import com.growbiz.backend.Responses.model.BasicRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AuthenticationRequest extends BasicRequest {
    private String password;
}
