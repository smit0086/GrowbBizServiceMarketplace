package com.growbiz.backend.UserAuthentication.controller;

import com.growbiz.backend.RequestResponse.Authentication.AuthenticationRequest;
import com.growbiz.backend.RequestResponse.Authentication.AuthenticationResponse;
import com.growbiz.backend.User.models.User;
import com.growbiz.backend.UserAuthentication.service.IUserAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserAuthenticationController {

    @Autowired
    IUserAuthenticationService authenticationService;

    @PostMapping(path = "/signup")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody User userInfo) {
        return ResponseEntity.ok(authenticationService.register(userInfo));
    }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authRequest));
    }

}
