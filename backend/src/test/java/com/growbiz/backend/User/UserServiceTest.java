package com.growbiz.backend.User;

import com.growbiz.backend.User.models.Role;
import com.growbiz.backend.User.models.User;
import com.growbiz.backend.User.repository.IUserRepository;
import com.growbiz.backend.User.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        User mockedUser = User.builder()
                .id(1L)
                .email("a@dal.ca")
                .password("password")
                .firstName("test")
                .lastName("test")
                .role(Role.PARTNER).build();
        when(userRepositoryMock.findByEmailAndRole(Mockito.anyString(), any(Role.class))).thenReturn(mockedUser);
    }

    @Test
    public void testGetUserByEmailAndRole() {

        User returnedUser = userServiceMock.getUserByEmailAndRole("a@dal.ca", Role.PARTNER.name());
        Assertions.assertEquals("a@dal.ca", returnedUser.getEmail());
        Assertions.assertEquals(Role.PARTNER, returnedUser.getRole());
        Assertions.assertEquals("test", returnedUser.getFirstName());
        Assertions.assertEquals("test", returnedUser.getLastName());
        Assertions.assertEquals("password", returnedUser.getPassword());
    }

    @Test
    public void testLoadUserByUsername() {
        UserDetails returnedUser = userServiceMock.loadUserByUsername("a@dal.ca" + ":" + Role.PARTNER.name());
        Assertions.assertEquals("a@dal.ca", returnedUser.getUsername());
        Assertions.assertEquals("password", returnedUser.getPassword());
        Assertions.assertEquals(List.of(new SimpleGrantedAuthority(Role.PARTNER.name())), returnedUser.getAuthorities());
    }

    @Test
    public void testUsernameNotFoundException() {
        when(userRepositoryMock.findByEmailAndRole(Mockito.anyString(), any(Role.class))).thenReturn(null);
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            userServiceMock.loadUserByUsername("a@dal.ca" + ":" + Role.PARTNER.name());
        });
    }
}
