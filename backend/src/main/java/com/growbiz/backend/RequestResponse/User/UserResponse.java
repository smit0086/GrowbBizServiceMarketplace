package com.growbiz.backend.RequestResponse.User;

import com.growbiz.backend.RequestResponse.Basic.BasicResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UserResponse extends BasicResponse {
    private String firstName;
    private String lastName;
}
