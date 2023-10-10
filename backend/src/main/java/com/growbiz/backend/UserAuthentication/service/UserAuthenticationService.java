package com.growbiz.backend.UserAuthentication.service;

import com.growbiz.backend.Security.service.JWTService;
import com.growbiz.backend.User.models.User;
import com.growbiz.backend.User.service.IUserService;
import com.growbiz.backend.UserAuthentication.model.AuthenticationRequest;
import com.growbiz.backend.UserAuthentication.model.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    @Override
    public AuthenticationResponse register(User userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userService.saveUser(userInfo);
        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(userInfo, userInfo.getRole().name()))
                .subject(userInfo.getEmail())
                .role(userInfo.getRole())
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (authenticationRequest.getEmail() + ":" + authenticationRequest.getRole().name(), authenticationRequest.getPassword()));
        User userInfo = userService.getUserByEmailAndRole(authenticationRequest.getEmail() + ":" + authenticationRequest.getRole().name());
        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(userInfo, authenticationRequest.getRole().name()))
                .subject(userInfo.getEmail())
                .role(authenticationRequest.getRole())
                .build();
    }
}
