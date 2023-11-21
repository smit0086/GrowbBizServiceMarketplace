package com.growbiz.backend.Business;

import com.growbiz.backend.Business.controller.BusinessController;
import com.growbiz.backend.Business.helper.BusinessControllerHelper;
import com.growbiz.backend.Business.models.Business;
import com.growbiz.backend.Business.service.IBusinessService;
import com.growbiz.backend.Email.service.ISendEmailService;
import com.growbiz.backend.Enums.BusinessStatus;
import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.RequestResponse.Business.BusinessResponse;
import com.growbiz.backend.TestConstants.TestConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BusinessControllerTest {
    @InjectMocks
    private BusinessController businessControllerMock;
    @Mock
    IBusinessService businessServiceMock;
    @Mock
    ISendEmailService sendEmailServiceMock;
    @Mock
    BusinessControllerHelper helperMock;
    Business mockedBusiness1;
    Business mockedBusiness2;
    BusinessResponse mockedBusinessResponse;

    @BeforeEach
    public void init() {
        mockedBusiness1 = Business.builder().businessId(1L)
                .businessName(TestConstants.TEST_BUSINESS_NAME)
                .description(TestConstants.TEST_BUSINESS_DESCRIPTION)
                .email(TestConstants.TEST_EMAIL)
                .fileURL(TestConstants.TEST_BUSINESS_FILE_PATH)
                .status(BusinessStatus.APPROVED)
                .build();
        mockedBusiness2 = Business.builder().businessId(2L)
                .businessName(TestConstants.TEST_BUSINESS_NAME)
                .description(TestConstants.TEST_BUSINESS_DESCRIPTION)
                .email(TestConstants.TEST_EMAIL)
                .fileURL(TestConstants.TEST_BUSINESS_FILE_PATH)
                .status(BusinessStatus.APPROVED)
                .build();
        mockedBusinessResponse = BusinessResponse.builder()
                .businesses(List.of(mockedBusiness1, mockedBusiness2))
                .subject(TestConstants.TEST_EMAIL)
                .role(Role.PARTNER)
                .build();
    }

    @Test
    void testGetAllBusinesses() {
        when(businessServiceMock.fetchBusinesses(BusinessStatus.APPROVED.name())).thenReturn(List.of(mockedBusiness1, mockedBusiness2));
        when(helperMock.createBusinessResponse(List.of(mockedBusiness1, mockedBusiness2))).thenReturn(ResponseEntity.ok().body(mockedBusinessResponse));
        ResponseEntity<BusinessResponse> actualResponse = businessControllerMock.getAllBusinesses(BusinessStatus.APPROVED.name());
        List<Business> actualBusinessList = Objects.requireNonNull(actualResponse.getBody()).getBusinesses();
        Assertions.assertEquals(actualBusinessList, List.of(mockedBusiness1, mockedBusiness2));
    }
}
