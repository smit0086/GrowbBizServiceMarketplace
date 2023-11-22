package com.growbiz.backend.UserAuthentication.service;

import com.growbiz.backend.Exception.exceptions.User.UserAlreadyExistsException;
import com.growbiz.backend.RequestResponse.Authentication.AuthenticationRequest;
import com.growbiz.backend.RequestResponse.Authentication.AuthenticationResponse;
import com.growbiz.backend.Security.service.JWTService;
import com.growbiz.backend.User.models.User;
import com.growbiz.backend.User.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j

public class UserAuthenticationService implements IUserAuthenticationService {


    @Autowired
    private final IUserService userService;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JWTService jwtService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    /**
     * Registers the user in the application
     *
     * @param userInfo - All the information about user
     * @return - response with a JWT token
     */
    @Override
    public AuthenticationResponse register(User userInfo) {
        if (Objects.nonNull(userService.getUserByEmailAndRole(userInfo.getEmail(), userInfo.getRole().name()))) {
            throw new UserAlreadyExistsException("The email you are trying to register is already registered");
        }
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userService.saveUser(userInfo);
        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(userInfo.getUsername(), userInfo.getRole().name()))
                .subject(userInfo.getEmail())
                .role(userInfo.getRole())
                .build();
    }

    /**
     * Authenticates the user based on Email, Role and Password.
     *
     * @param authenticationRequest - authentication request
     * @return - response with JWT Token
     */
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (authenticationRequest.getEmail() + ":" + authenticationRequest.getRole().name(), authenticationRequest.getPassword()));

        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(authenticationRequest.getEmail(), authenticationRequest.getRole().name()))
                .subject(authenticationRequest.getEmail())
                .role(authenticationRequest.getRole())
                .build();
    }
}
