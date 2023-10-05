package com.growbiz.backend.UserAuthentication.controller;

import com.growbiz.backend.User.models.Customer;
import com.growbiz.backend.User.models.Partner;
import com.growbiz.backend.UserAuthentication.model.AuthenticationRequest;
import com.growbiz.backend.UserAuthentication.model.AuthenticationResponse;
import com.growbiz.backend.UserAuthentication.service.IUserAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserAuthenticationController {

    @Autowired
    IUserAuthenticationService authenticationService;

    @PostMapping(path = "/signupCustomer")
    public ResponseEntity<AuthenticationResponse> resigerUser(@RequestBody Customer customer) {
        return ResponseEntity.ok(authenticationService.register(customer));
    }

    @PostMapping(path = "/signupPartner")
    public ResponseEntity<AuthenticationResponse> resigerUser(@RequestBody Partner partner) {
        return ResponseEntity.ok(authenticationService.register(partner));
    }

    @PostMapping(path = "/authenticateCustomer?role={role}")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authRequest, @RequestParam String role) {
        return ResponseEntity.ok(authenticationService.authenticate(authRequest, role));
    }
}
