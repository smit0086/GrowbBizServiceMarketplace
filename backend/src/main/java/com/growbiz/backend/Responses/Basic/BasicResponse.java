package com.growbiz.backend.Responses.Basic;

import com.growbiz.backend.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BasicResponse {

    private String subject;
    private Role role;
}
