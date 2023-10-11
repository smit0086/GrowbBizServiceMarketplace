package com.growbiz.backend.Responses.model;

import com.growbiz.backend.User.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BasicResponse {
    private String token;
    private String subject;
    private Role role;
}
