package com.growbiz.backend.BusinessHour;

import com.growbiz.backend.BusinessHour.controller.BusinessHourController;
import com.growbiz.backend.BusinessHour.model.BusinessHour;
import com.growbiz.backend.BusinessHour.service.IBusinessHourService;
import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.RequestResponse.BusinessHour.BusinessHourRequest;
import com.growbiz.backend.RequestResponse.BusinessHour.BusinessHourResponse;
import com.growbiz.backend.TestConstants.TestConstants;
import com.growbiz.backend.User.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BusinessHourControllerTest {
    @InjectMocks
    BusinessHourController businessHourControllerMock;
    @Mock
    private IBusinessHourService businessHourService;
    @Mock
    BusinessHour mockBusinessHour;
    @Mock
    User mockUser;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        mockUser = User
                .builder()
                .id(1L)
                .email(TestConstants.TEST_EMAIL)
                .password(TestConstants.TEST_PASSWORD)
                .firstName(TestConstants.TEST_NAME)
                .lastName(TestConstants.TEST_NAME)
                .role(Role.CUSTOMER)
                .build();
        Authentication mockedAuthentication = mock(Authentication.class);
        SecurityContext mockedSecurityContext = mock(SecurityContext.class);
        when(mockedSecurityContext.getAuthentication()).thenReturn(mockedAuthentication);
        SecurityContextHolder.setContext(mockedSecurityContext);
        when(mockedAuthentication.getPrincipal()).thenReturn(mockUser);
    }

    @Test
    void getBusinessHoursTest() {
        ResponseEntity<BusinessHourResponse> actualResponse;
        mockBusinessHour = BusinessHour.builder()
                .monday_start(TestConstants.TEST_START_LOCAL_TIME)
                .monday_end(TestConstants.TEST_END_LOCAL_TIME)
                .tuesday_start(TestConstants.TEST_START_LOCAL_TIME)
                .tuesday_end(TestConstants.TEST_END_LOCAL_TIME)
                .wednesday_start(TestConstants.TEST_START_LOCAL_TIME)
                .wednesday_end(TestConstants.TEST_END_LOCAL_TIME)
                .thursday_start(TestConstants.TEST_START_LOCAL_TIME)
                .thursday_end(TestConstants.TEST_END_LOCAL_TIME)
                .friday_start(TestConstants.TEST_START_LOCAL_TIME)
                .friday_end(TestConstants.TEST_END_LOCAL_TIME)
                .saturday_start(TestConstants.TEST_START_LOCAL_TIME)
                .saturday_end(TestConstants.TEST_END_LOCAL_TIME)
                .sunday_start(TestConstants.TEST_START_LOCAL_TIME)
                .sunday_end(TestConstants.TEST_END_LOCAL_TIME)
                .build();
        ResponseEntity<BusinessHourResponse> expectedResponse = ResponseEntity.ok(
                BusinessHourResponse.builder()
                        .businessHour(mockBusinessHour)
                        .subject(mockUser.getEmail())
                        .role(mockUser.getRole())
                        .build()
        );

        when(businessHourService.getBusinessHour(1L)).thenReturn(mockBusinessHour);
        actualResponse = businessHourControllerMock.getBusinessHours("1");
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void updateBusinessHoursTest() {
        ResponseEntity<String> actualResponse;
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Updated");
        BusinessHourRequest mockBHR = mock(BusinessHourRequest.class);

        actualResponse = businessHourControllerMock.updateBusinessHour(mockBHR);
        assertEquals(expectedResponse, actualResponse);
    }
}
