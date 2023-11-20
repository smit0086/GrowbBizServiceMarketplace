package com.growbiz.backend.RequestResponse.Authentication;

import com.growbiz.backend.RequestResponse.Basic.BasicRequest;
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
