package com.growbiz.backend.Payment.model;

import com.growbiz.backend.Responses.model.BasicResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class PaymentResponse extends BasicResponse {
    String clientSecret;
    Long paymentId;
}
