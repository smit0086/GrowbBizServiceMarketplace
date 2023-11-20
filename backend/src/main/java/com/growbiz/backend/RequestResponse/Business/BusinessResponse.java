package com.growbiz.backend.RequestResponse.Business;

import com.growbiz.backend.Business.models.Business;
import com.growbiz.backend.RequestResponse.Basic.BasicResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class BusinessResponse extends BasicResponse {

    private List<Business> businesses;
}
