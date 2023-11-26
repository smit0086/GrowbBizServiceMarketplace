package com.growbiz.backend.Business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.growbiz.backend.Business.helper.BusinessControllerHelper;
import com.growbiz.backend.Business.models.Business;
import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Enums.BusinessStatus;
import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.RequestResponse.Business.BusinessRequest;
import com.growbiz.backend.RequestResponse.Business.BusinessResponse;
import com.growbiz.backend.RequestResponse.Business.VerificationRequest;
import com.growbiz.backend.TestConstants.TestConstants;
import com.growbiz.backend.User.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BusinessControllerHelperTest {

    private final static long TEST_USER_ID = 1L;
    private final static long TEST_CATEGORY_ID = 1L;
    private final static long TEST_BUSINESS_ID = 1L;

    @InjectMocks
    private BusinessControllerHelper businessControllerHelper;

    private Category mockCategory;

    private Business mockBusiness;

    private User mockUser;

    @BeforeEach
    public void init() {
        mockUser = User
                .builder()
                .id(TEST_USER_ID)
                .email(TestConstants.TEST_EMAIL)
                .password(TestConstants.TEST_PASSWORD)
                .firstName("John")
                .lastName("Doe")
                .role(Role.PARTNER)
                .build();

        mockCategory = Category.builder()
                .categoryID(TEST_CATEGORY_ID)
                .name(TestConstants.TEST_CATEGORY_NAME)
                .tax(TestConstants.TEST_CATEGORY_TAX)
                .build();

        mockBusiness = Business.builder()
                .businessId(TEST_BUSINESS_ID)
                .businessName(TestConstants.TEST_BUSINESS_NAME)
                .email(TestConstants.TEST_EMAIL)
                .status(BusinessStatus.APPROVED)
                .category(mockCategory)
                .fileURL(TestConstants.TEST_BUSINESS_FILE_PATH)
                .description(TestConstants.TEST_BUSINESS_DESCRIPTION)
                .reason("none")
                .build();
    }

    @Test
    public void createBusinessResponseTest() {
        ResponseEntity<BusinessResponse> actualResponse;
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        List<Business> businessList = List.of(mockBusiness);
        ResponseEntity<BusinessResponse> expectedResponse = ResponseEntity.ok(
                BusinessResponse.builder()
                        .businesses(businessList)
                        .subject(TestConstants.TEST_EMAIL)
                        .role(Role.PARTNER)
                        .build()
        );

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);
        actualResponse = businessControllerHelper.createBusinessResponse(businessList);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getBusinessRequestFromJSON() throws JsonProcessingException {
        String json = "";
        MultipartFile mockFile = mock(MultipartFile.class);
        BusinessRequest expectedBusinessReq = BusinessRequest.builder()
                .businessName(TestConstants.TEST_BUSINESS_NAME)
                .categoryId(TEST_CATEGORY_ID)
                .file(mockFile)
                .description("test")
                .build();
        MockedConstruction<ObjectMapper> mockObjMapConstr = Mockito.mockConstruction(ObjectMapper.class,
                (mock, context) -> {
            given(mock.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)).willReturn(mock);
            given(mock.readValue(json, BusinessRequest.class)).willReturn(expectedBusinessReq);
                });
        BusinessRequest actualBusinessReq = businessControllerHelper.getBusinessRequestFromJSON(json, mockFile);
        assertEquals(expectedBusinessReq, actualBusinessReq);
    }

    @Test
    public void getEmailBodyApprovalTest() {
        VerificationRequest mockVr = VerificationRequest.builder()
                .status(BusinessStatus.APPROVED)
                .build();

        String actualMessage = businessControllerHelper.getEmailBody(mockVr);
        assertNotNull(actualMessage);
    }

    @Test
    public void getEmailBodyDeclinedTest() {
        VerificationRequest mockVr = VerificationRequest.builder()
                .status(BusinessStatus.DECLINED)
                .build();

        String actualMessage = businessControllerHelper.getEmailBody(mockVr);
        assertNotNull(actualMessage);
    }
}
