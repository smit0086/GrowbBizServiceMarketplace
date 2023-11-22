package com.growbiz.backend.User.controller;

import com.growbiz.backend.RequestResponse.User.UserResponse;
import com.growbiz.backend.User.models.User;
import com.growbiz.backend.User.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    /**
     * This returns user information based on the email and role if the user exists.
     *
     * @param email - email of the user
     * @param role  - role of the user
     * @return - ResponseEntity with User information
     */
    @GetMapping
    public ResponseEntity<UserResponse> getUser(@RequestParam("email") String email, @RequestParam("role") String role) {
        User user = userService.getUserByEmailAndRole(email, role);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("No Username exists with email: " + email);
        }
        UserResponse userResponse = UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .subject(user.getEmail())
                .role(user.getRole())
                .build();
        return ResponseEntity.ok().body(userResponse);
    }
}
