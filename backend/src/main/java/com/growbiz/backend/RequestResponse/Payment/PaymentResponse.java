package com.growbiz.backend.RequestResponse.Payment;

import com.growbiz.backend.RequestResponse.Basic.BasicResponse;
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
