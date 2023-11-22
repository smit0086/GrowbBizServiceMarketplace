package com.growbiz.backend.User;

import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.TestConstants.TestConstants;
import com.growbiz.backend.User.models.User;
import com.growbiz.backend.User.repository.IUserRepository;
import com.growbiz.backend.User.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userServiceMock;

    @Mock
    private IUserRepository userRepositoryMock;

    @Mock
    User mockedUser;

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
    public void testGetUserByEmailAndRole() {
        when(userRepositoryMock.findByEmailAndRole(Mockito.anyString(), any(Role.class))).thenReturn(mockedUser);
        User returnedUser = userServiceMock.getUserByEmailAndRole(TestConstants.TEST_EMAIL, Role.PARTNER.name());
        Assertions.assertEquals(TestConstants.TEST_EMAIL, returnedUser.getEmail());
        Assertions.assertEquals(Role.PARTNER, returnedUser.getRole());
        Assertions.assertEquals(TestConstants.TEST_NAME, returnedUser.getFirstName());
        Assertions.assertEquals(TestConstants.TEST_NAME, returnedUser.getLastName());
        Assertions.assertEquals(TestConstants.TEST_PASSWORD, returnedUser.getPassword());
    }

    @Test
    public void testLoadUserByUsername() {
        when(userRepositoryMock.findByEmailAndRole(Mockito.anyString(), any(Role.class))).thenReturn(mockedUser);
        UserDetails returnedUser = userServiceMock.loadUserByUsername(TestConstants.TEST_EMAIL + ":" + Role.PARTNER.name());
        Assertions.assertEquals(TestConstants.TEST_EMAIL, returnedUser.getUsername());
        Assertions.assertEquals(TestConstants.TEST_PASSWORD, returnedUser.getPassword());
        Assertions.assertEquals(List.of(new SimpleGrantedAuthority(Role.PARTNER.name())), returnedUser.getAuthorities());
    }

    @Test
    public void testUsernameNotFoundException() {
        when(userRepositoryMock.findByEmailAndRole(Mockito.anyString(), any(Role.class))).thenReturn(null);
        Assertions.assertThrows(UsernameNotFoundException.class, () ->
                userServiceMock.loadUserByUsername(TestConstants.TEST_EMAIL + ":" + Role.PARTNER.name())
        );
    }
}
