package com.growbiz.backend.UserAuthentication.controller;

import com.growbiz.backend.User.models.User;
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

    @PostMapping(path = "/signup")
    public ResponseEntity<AuthenticationResponse> resigerUser(@RequestBody User userInfo) {
        return ResponseEntity.ok(authenticationService.register(userInfo));
    }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authRequest, @RequestParam("role") String role) {
        return ResponseEntity.ok(authenticationService.authenticate(authRequest, role));
    }

}
