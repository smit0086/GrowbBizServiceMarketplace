package com.growbiz.backend.RequestResponse.Authentication;

import com.growbiz.backend.RequestResponse.Basic.BasicResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AuthenticationResponse extends BasicResponse {
    private String token;

}