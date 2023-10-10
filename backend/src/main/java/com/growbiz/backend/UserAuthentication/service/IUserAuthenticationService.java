package com.growbiz.backend.UserAuthentication.service;


import com.growbiz.backend.User.models.User;
import com.growbiz.backend.UserAuthentication.model.AuthenticationRequest;
import com.growbiz.backend.UserAuthentication.model.AuthenticationResponse;

public interface IUserAuthenticationService {
    public AuthenticationResponse register(User userInfo);

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
