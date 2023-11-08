package com.growbiz.backend.UserAuthentication;


import com.growbiz.backend.Exception.exceptions.UserAlreadyExistsException;
import com.growbiz.backend.Security.service.JWTService;
import com.growbiz.backend.User.models.Role;
import com.growbiz.backend.User.models.User;
import com.growbiz.backend.User.service.IUserService;
import com.growbiz.backend.UserAuthentication.model.AuthenticationRequest;
import com.growbiz.backend.UserAuthentication.model.AuthenticationResponse;
import com.growbiz.backend.UserAuthentication.service.UserAuthenticationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserAuthenticationServiceTest {
    @InjectMocks
    private UserAuthenticationService userAuthenticationServiceMock;
    @Mock
    private IUserService userServiceMock;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JWTService jwtServiceMock;
    @Mock
    private AuthenticationManager authenticationManagerMock;
    User mockedUser;
    private AuthenticationResponse expectedResponse;

    @Before
    public void init() throws Exception {
        MockitoAnnotations.openMocks(this);
        expectedResponse = AuthenticationResponse.builder()
                .token("tokenReturned")
                .role(Role.PARTNER)
                .subject("testEmail@dal.ca")
                .build();
        mockedUser = User.builder()
                .id(1L)
                .email("testEmail@dal.ca")
                .password("password")
                .firstName("test")
                .lastName("test")
                .role(Role.PARTNER).build();
        when(userServiceMock.getUserByEmailAndRole(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        doNothing().when(userServiceMock).saveUser(any(User.class));
        when(jwtServiceMock.generateToken(anyString(), anyString())).thenReturn("tokenReturned");
        when(passwordEncoder.encode(mockedUser.getPassword())).thenReturn(mockedUser.getPassword());
        Authentication mockAuthentication = new UsernamePasswordAuthenticationToken("testEmail@dal.ca", "password");
        when(authenticationManagerMock.authenticate(mockAuthentication)).thenReturn(mockAuthentication);
    }

    @Test
    public void testRegister() {
        AuthenticationResponse actualResponse = userAuthenticationServiceMock.register(mockedUser);
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testRegisterUserAlreadyExistsException() {
        when(userServiceMock.getUserByEmailAndRole(Mockito.anyString(), Mockito.anyString())).thenReturn(mockedUser);
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userAuthenticationServiceMock.register(mockedUser));
    }

    @Test
    public void testAuthenticate() {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder().email("testEmail@dal.ca").role(Role.PARTNER).password("password").build();
        Assertions.assertEquals(expectedResponse, userAuthenticationServiceMock.authenticate(authenticationRequest));
    }
}
