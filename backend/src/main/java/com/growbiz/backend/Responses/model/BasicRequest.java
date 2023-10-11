package com.growbiz.backend.Responses.model;

import com.growbiz.backend.User.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class BasicRequest {
    private String email;
    private Role role;
}
