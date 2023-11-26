package com.growbiz.backend.RequestResponse.Business;

import com.growbiz.backend.Enums.BusinessStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerificationRequest {
    BusinessStatus status;
    String reason;
}
