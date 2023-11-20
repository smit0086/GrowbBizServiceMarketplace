package com.growbiz.backend.Email.model;

import com.growbiz.backend.Responses.model.BasicRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.mail.SimpleMailMessage;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmailRequest extends BasicRequest {
    private long bookingId;
}
