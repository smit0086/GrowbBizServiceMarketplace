package com.growbiz.backend.UserAuthentication;

import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.RequestResponse.Authentication.AuthenticationRequest;
import com.growbiz.backend.RequestResponse.Authentication.AuthenticationResponse;
import com.growbiz.backend.TestConstants.TestConstants;
import com.growbiz.backend.User.models.User;
import com.growbiz.backend.UserAuthentication.controller.UserAuthenticationController;
import com.growbiz.backend.UserAuthentication.service.IUserAuthenticationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

public class UserAuthenticationControllerTest {

    @InjectMocks
    private UserAuthenticationController userAuthenticationController;

    @Mock
    private IUserAuthenticationService userAuthenticationServiceMocked;

    @Mock
    private ResponseEntity<AuthenticationResponse> expectedResponse;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        expectedResponse = ResponseEntity.ok(AuthenticationResponse.builder()
                .token(TestConstants.TEST_TOKEN)
                .role(Role.PARTNER)
                .subject(TestConstants.TEST_EMAIL)
                .build());
    }

    @Test
    public void testAuthenticate() {
        AuthenticationRequest mockAuthenticationRequest = AuthenticationRequest.builder().email(TestConstants.TEST_EMAIL).role(Role.PARTNER).password(TestConstants.TEST_PASSWORD).build();
        when(userAuthenticationServiceMocked.authenticate(mockAuthenticationRequest)).thenReturn(AuthenticationResponse.builder()
                .token(TestConstants.TEST_TOKEN)
                .role(Role.PARTNER)
                .subject(TestConstants.TEST_EMAIL)
                .build());
        Assertions.assertEquals(expectedResponse, userAuthenticationController.authenticate(mockAuthenticationRequest));
    }

    @Test
    public void testRegisterUser() {
        User mockedUser = User.builder()
                .id(1L)
                .email(TestConstants.TEST_EMAIL)
                .password(TestConstants.TEST_PASSWORD)
                .firstName(TestConstants.TEST_NAME)
                .lastName(TestConstants.TEST_NAME)
                .role(Role.PARTNER).build();
        when(userAuthenticationServiceMocked.register(mockedUser)).thenReturn(AuthenticationResponse.builder()
                .token(TestConstants.TEST_TOKEN)
                .role(Role.PARTNER)
                .subject(TestConstants.TEST_EMAIL)
                .build());
        Assertions.assertEquals(expectedResponse, userAuthenticationController.registerUser(mockedUser));
    }
}
