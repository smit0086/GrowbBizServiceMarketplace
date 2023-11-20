package com.growbiz.backend.UserAuthentication;

import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.RequestResponse.Authentication.AuthenticationRequest;
import com.growbiz.backend.RequestResponse.Authentication.AuthenticationResponse;
import com.growbiz.backend.User.models.User;
import com.growbiz.backend.UserAuthentication.controller.UserAuthenticationController;
import com.growbiz.backend.UserAuthentication.service.IUserAuthenticationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
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

    private ResponseEntity<AuthenticationResponse> expectedResponse;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        expectedResponse = ResponseEntity.ok(AuthenticationResponse.builder()
                .token("tokenReturned")
                .role(Role.PARTNER)
                .subject("testEmail@dal.ca")
                .build());
    }

    @Test
    public void testAuthenticate() {
        AuthenticationRequest mockAuthenticationRequest = AuthenticationRequest.builder().email("testEmail@dal.ca").role(Role.PARTNER).password("password").build();
        when(userAuthenticationServiceMocked.authenticate(mockAuthenticationRequest)).thenReturn(AuthenticationResponse.builder()
                .token("tokenReturned")
                .role(Role.PARTNER)
                .subject("testEmail@dal.ca")
                .build());
        Assertions.assertEquals(expectedResponse, userAuthenticationController.authenticate(mockAuthenticationRequest));
    }

    @Test
    public void testRegisterUser() {
        User mockedUser = User.builder()
                .id(1L)
                .email("a@dal.ca")
                .password("password")
                .firstName("test")
                .lastName("test")
                .role(Role.PARTNER).build();
        when(userAuthenticationServiceMocked.register(mockedUser)).thenReturn(AuthenticationResponse.builder()
                .token("tokenReturned")
                .role(Role.PARTNER)
                .subject("testEmail@dal.ca")
                .build());
        Assertions.assertEquals(expectedResponse, userAuthenticationController.registerUser(mockedUser));
    }
}
