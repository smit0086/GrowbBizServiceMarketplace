package com.growbiz.backend.Responses.Basic;

import com.growbiz.backend.Enums.Role;
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
