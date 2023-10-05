package com.growbiz.backend.UserAuthentication.service;

import com.growbiz.backend.Security.service.JWTService;
import com.growbiz.backend.User.models.Customer;
import com.growbiz.backend.User.models.Partner;
import com.growbiz.backend.User.service.ICustomerService;
import com.growbiz.backend.User.service.IPartnerService;
import com.growbiz.backend.UserAuthentication.model.AuthenticationRequest;
import com.growbiz.backend.UserAuthentication.model.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthenticationService implements IUserAuthenticationService {

    @Autowired
    private final IPartnerService partnerService;

    @Autowired
    private final ICustomerService customerService;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JWTService jwtService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerService.saveCustomer(customer);
        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(customer))
                .subject(customer.getEmail())
                .build();
    }

    @Override
    public AuthenticationResponse register(Partner partner) {
        partner.setPassword(passwordEncoder.encode(partner.getPassword()));
        partnerService.savePartner(partner);
        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(partner))
                .subject(partner.getEmail())
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest, String role) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        if (role.equalsIgnoreCase("customer")) {
            Customer customer = customerService.getCustomerByEmail(authenticationRequest.getEmail());
            return AuthenticationResponse.builder()
                    .token(jwtService.generateToken(customer))
                    .subject(customer.getEmail())
                    .build();
        }
        Partner partner = partnerService.getPartnerByEmail(authenticationRequest.getEmail());

        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(partner))
                .subject(partner.getEmail())
                .build();
    }
}
