package com.growbiz.backend.User;

import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.RequestResponse.User.UserResponse;
import com.growbiz.backend.TestConstants.TestConstants;
import com.growbiz.backend.User.controller.UserController;
import com.growbiz.backend.User.models.User;
import com.growbiz.backend.User.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userControllerMock;

    @Mock
    private UserService userServiceMock;
    @Mock
    User mockedUser;
    @Mock
    UserResponse mockedUserResponse;

    @BeforeEach
    public void init() {
        mockedUser = User.builder()
                .id(TestConstants.TEST_ID_1)
                .email(TestConstants.TEST_EMAIL)
                .password(TestConstants.TEST_PASSWORD)
                .firstName(TestConstants.TEST_NAME)
                .lastName(TestConstants.TEST_NAME)
                .role(Role.PARTNER).build();
        mockedUserResponse = UserResponse.builder()
                .firstName(TestConstants.TEST_NAME)
                .lastName(TestConstants.TEST_NAME)
                .subject(TestConstants.TEST_EMAIL)
                .role(Role.PARTNER)
                .build();
    }

    @Test
    void testGetUser() {
        when(userServiceMock.getUserByEmailAndRole(TestConstants.TEST_EMAIL, Role.PARTNER.name())).thenReturn(mockedUser);
        ResponseEntity<UserResponse> expectedResponse = ResponseEntity.ok(mockedUserResponse);
        ResponseEntity<UserResponse> actualResponse = userControllerMock.getUser(TestConstants.TEST_EMAIL, Role.PARTNER.name());
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testGetUserException() {
        when(userServiceMock.getUserByEmailAndRole(TestConstants.TEST_EMAIL, Role.PARTNER.name())).thenReturn(null);
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userControllerMock.getUser(TestConstants.TEST_EMAIL, Role.PARTNER.name()));
    }
}
