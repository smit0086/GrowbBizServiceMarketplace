package com.growbiz.backend.UserAuthentication.service;


import com.growbiz.backend.RequestResponse.Authentication.AuthenticationRequest;
import com.growbiz.backend.RequestResponse.Authentication.AuthenticationResponse;
import com.growbiz.backend.User.models.User;

public interface IUserAuthenticationService {
    public AuthenticationResponse register(User userInfo);

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
