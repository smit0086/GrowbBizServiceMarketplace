package com.growbiz.backend.RequestResponse.Business;

import com.growbiz.backend.RequestResponse.Basic.BasicRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BusinessRequest extends BasicRequest {

    private String businessName;

    private Long categoryId;

    private MultipartFile file;

    private String description;

}
