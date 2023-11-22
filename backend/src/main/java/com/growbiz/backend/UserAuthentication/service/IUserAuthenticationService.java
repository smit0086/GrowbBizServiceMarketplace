package com.growbiz.backend.UserAuthentication.service;


import com.growbiz.backend.RequestResponse.Authentication.AuthenticationRequest;
import com.growbiz.backend.RequestResponse.Authentication.AuthenticationResponse;
import com.growbiz.backend.User.models.User;

public interface IUserAuthenticationService {
    
    /**
     * Registers the user in the application
     *
     * @param userInfo - All the information about user
     * @return - response with a JWT token
     */
    public AuthenticationResponse register(User userInfo);

    /**
     * Authenticates the user based on Email, Role and Password.
     *
     * @param authenticationRequest - authentication request
     * @return - response with JWT Token
     */
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
