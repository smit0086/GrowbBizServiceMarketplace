package com.growbiz.backend.UserAuthentication;


import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.Exception.exceptions.User.UserAlreadyExistsException;
import com.growbiz.backend.RequestResponse.Authentication.AuthenticationRequest;
import com.growbiz.backend.RequestResponse.Authentication.AuthenticationResponse;
import com.growbiz.backend.Security.service.JWTService;
import com.growbiz.backend.TestConstants.TestConstants;
import com.growbiz.backend.User.models.User;
import com.growbiz.backend.User.service.IUserService;
import com.growbiz.backend.UserAuthentication.service.UserAuthenticationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    @BeforeEach
    public void init() {
        mockedUser = User.builder()
                .id(1L)
                .email(TestConstants.TEST_EMAIL)
                .password(TestConstants.TEST_PASSWORD)
                .firstName(TestConstants.TEST_NAME)
                .lastName(TestConstants.TEST_NAME)
                .role(Role.PARTNER).build();
    }

    @Test
    public void testRegister() {
        expectedResponse = AuthenticationResponse.builder()
                .token(TestConstants.TEST_TOKEN)
                .role(Role.PARTNER)
                .subject(TestConstants.TEST_EMAIL)
                .build();
        when(userServiceMock.getUserByEmailAndRole(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        doNothing().when(userServiceMock).saveUser(any(User.class));
        when(jwtServiceMock.generateToken(anyString(), anyString())).thenReturn(TestConstants.TEST_TOKEN);
        when(passwordEncoder.encode(mockedUser.getPassword())).thenReturn(mockedUser.getPassword());
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
        expectedResponse = AuthenticationResponse.builder()
                .token(TestConstants.TEST_TOKEN)
                .role(Role.PARTNER)
                .subject(TestConstants.TEST_EMAIL)
                .build();
        when(jwtServiceMock.generateToken(anyString(), anyString())).thenReturn(TestConstants.TEST_TOKEN);
        Authentication mockAuthentication = new UsernamePasswordAuthenticationToken(TestConstants.TEST_EMAIL + ":" + Role.PARTNER.name(), TestConstants.TEST_PASSWORD);
        when(authenticationManagerMock.authenticate(mockAuthentication)).thenReturn(mockAuthentication);
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder().email(TestConstants.TEST_EMAIL).role(Role.PARTNER).password(TestConstants.TEST_PASSWORD).build();
        Assertions.assertEquals(expectedResponse, userAuthenticationServiceMock.authenticate(authenticationRequest));
    }
}
