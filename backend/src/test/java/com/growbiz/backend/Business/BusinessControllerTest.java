package com.growbiz.backend.Business;

import com.growbiz.backend.Business.controller.BusinessController;
import com.growbiz.backend.Business.helper.BusinessControllerHelper;
import com.growbiz.backend.Business.models.Business;
import com.growbiz.backend.Business.service.IBusinessService;
import com.growbiz.backend.Email.service.ISendEmailService;
import com.growbiz.backend.Enums.BusinessStatus;
import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.RequestResponse.Business.BusinessRequest;
import com.growbiz.backend.RequestResponse.Business.BusinessResponse;
import com.growbiz.backend.RequestResponse.Business.VerificationRequest;
import com.growbiz.backend.TestConstants.TestConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BusinessControllerTest {
    public static final long BUSINESS_ID = 2L;
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
    private static final String TEST_BODY = "TestBody";
    private static final String TEST_FILE_CONTENT = "Mocked file content";
    private static final String TEST_VERIFICATION_STRING = "Business " + TestConstants.TEST_BUSINESS_NAME + " has been " + BusinessStatus.APPROVED + "! Email has been sent to the Partner";

    @BeforeEach
    public void init() {
        mockedBusiness1 = Business.builder().businessId(1L)
                .businessName(TestConstants.TEST_BUSINESS_NAME)
                .description(TestConstants.TEST_BUSINESS_DESCRIPTION)
                .email(TestConstants.TEST_EMAIL)
                .fileURL(TestConstants.TEST_BUSINESS_FILE_PATH)
                .status(BusinessStatus.APPROVED)
                .build();
        mockedBusiness2 = Business.builder().businessId(BUSINESS_ID)
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
        mockedBusinessResponse = BusinessResponse.builder()
                .businesses(List.of(mockedBusiness1, mockedBusiness2))
                .subject(TestConstants.TEST_EMAIL)
                .role(Role.PARTNER)
                .build();
        when(businessServiceMock.fetchBusinesses(BusinessStatus.APPROVED.name())).thenReturn(List.of(mockedBusiness1, mockedBusiness2));
        when(helperMock.createBusinessResponse(List.of(mockedBusiness1, mockedBusiness2))).thenReturn(ResponseEntity.ok().body(mockedBusinessResponse));
        ResponseEntity<BusinessResponse> actualResponse = businessControllerMock.getAllBusinesses(BusinessStatus.APPROVED.name());
        List<Business> actualBusinessList = Objects.requireNonNull(actualResponse.getBody()).getBusinesses();
        Assertions.assertEquals(List.of(mockedBusiness1, mockedBusiness2), actualBusinessList);
    }

    @Test
    void testGetBusiness() {
        mockedBusinessResponse = BusinessResponse.builder()
                .businesses(List.of(mockedBusiness1))
                .subject(TestConstants.TEST_EMAIL)
                .role(Role.PARTNER)
                .build();
        when(businessServiceMock.findByEmail(anyString())).thenReturn(mockedBusiness1);
        when(helperMock.createBusinessResponse(anyList())).thenReturn(ResponseEntity.ok().body(mockedBusinessResponse));
        ResponseEntity<BusinessResponse> actualResponse = businessControllerMock.getBusiness(TestConstants.TEST_EMAIL);
        List<Business> actualBusinessList = Objects.requireNonNull(actualResponse.getBody()).getBusinesses();
        Assertions.assertEquals(actualBusinessList, List.of(mockedBusiness1));
    }

    @Test
    void testSaveBusiness() {
        BusinessRequest mockBusinessRequest = mock(BusinessRequest.class);
        MockMultipartFile mockMultipartFile = mock(MockMultipartFile.class);
        mockedBusinessResponse = BusinessResponse.builder()
                .businesses(List.of(mockedBusiness1))
                .subject(TestConstants.TEST_EMAIL)
                .role(Role.PARTNER)
                .build();
        when(helperMock.getBusinessRequestFromJSON(anyString(), any(MultipartFile.class))).thenReturn(mockBusinessRequest);
        when(businessServiceMock.save(mockBusinessRequest)).thenReturn(mockedBusiness1);
        when(helperMock.createBusinessResponse(anyList())).thenReturn(ResponseEntity.ok().body(mockedBusinessResponse));
        ResponseEntity<BusinessResponse> actualResponse = businessControllerMock.saveBusiness(TestConstants.TEST_JSON, mockMultipartFile);
        List<Business> actualBusinessList = Objects.requireNonNull(actualResponse.getBody()).getBusinesses();
        Assertions.assertEquals(actualBusinessList, List.of(mockedBusiness1));
    }

    @Test
    void testUpdateBusiness() {
        BusinessRequest mockBusinessRequest = mock(BusinessRequest.class);
        MockMultipartFile mockMultipartFile = mock(MockMultipartFile.class);
        mockedBusinessResponse = BusinessResponse.builder()
                .businesses(List.of(mockedBusiness1))
                .subject(TestConstants.TEST_EMAIL)
                .role(Role.PARTNER)
                .build();
        when(helperMock.getBusinessRequestFromJSON(anyString(), any(MultipartFile.class))).thenReturn(mockBusinessRequest);
        when(businessServiceMock.updateBusiness(mockBusinessRequest, 1L)).thenReturn(mockedBusiness1);
        when(helperMock.createBusinessResponse(anyList())).thenReturn(ResponseEntity.ok().body(mockedBusinessResponse));
        ResponseEntity<BusinessResponse> actualResponse = businessControllerMock.updateBusiness(TestConstants.TEST_JSON, mockMultipartFile, 1L);
        List<Business> actualBusinessList = Objects.requireNonNull(actualResponse.getBody()).getBusinesses();
        Assertions.assertEquals(actualBusinessList, List.of(mockedBusiness1));
    }

    @Test
    void testVerifyBusiness() {
        VerificationRequest mockVerificationRequest = VerificationRequest.builder()
                .status(BusinessStatus.APPROVED)
                .reason(TestConstants.TEST_NOTE)
                .build();
        when(businessServiceMock.findById(1L)).thenReturn(mockedBusiness1);
        when(helperMock.getEmailBody(mockVerificationRequest)).thenReturn(TEST_BODY);
        doNothing().when(businessServiceMock).save(mockedBusiness1);
        doNothing().when(sendEmailServiceMock).sendEmail(anyString(), anyString(), anyString());
        ResponseEntity<String> expectedResponseEntity = ResponseEntity.ok(TEST_VERIFICATION_STRING);
        ResponseEntity<String> actualResponse = businessControllerMock.verifyBusiness(1L, mockVerificationRequest);
        Assertions.assertEquals(expectedResponseEntity, actualResponse);
    }

    @Test
    void testDownloadFile() {
        when(businessServiceMock.downloadFile(anyString())).thenReturn(TEST_FILE_CONTENT.getBytes());
        ResponseEntity<byte[]> expectedResponseEntity = ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(TEST_FILE_CONTENT.getBytes());
        ResponseEntity<byte[]> actualResponseEntity = businessControllerMock.downloadFile(TestConstants.TEST_EMAIL);
        Assertions.assertEquals(expectedResponseEntity, actualResponseEntity);
    }
}
