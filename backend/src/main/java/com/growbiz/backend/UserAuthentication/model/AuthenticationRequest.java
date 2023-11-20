package com.growbiz.backend.UserAuthentication.model;

import com.growbiz.backend.Responses.Basic.BasicRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AuthenticationRequest extends BasicRequest {
    private String password;
}
