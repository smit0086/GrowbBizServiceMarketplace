package com.growbiz.backend.Business.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerificationRequest {
    BusinessStatus status;
    String reason;
}
