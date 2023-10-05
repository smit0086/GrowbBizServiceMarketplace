package com.growbiz.backend.UserAuthentication.service;


import com.growbiz.backend.User.models.Customer;
import com.growbiz.backend.User.models.Partner;
import com.growbiz.backend.UserAuthentication.model.AuthenticationRequest;
import com.growbiz.backend.UserAuthentication.model.AuthenticationResponse;

public interface IUserAuthenticationService {
    public AuthenticationResponse register(Customer customer);

    public AuthenticationResponse register(Partner partner);

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest, String role);
}
