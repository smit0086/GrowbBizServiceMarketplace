package com.growbiz.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.growbiz.backend.models.Role;
import com.growbiz.backend.models.RoleType;
import com.growbiz.backend.models.User;
import com.growbiz.backend.repository.RoleRepository;
import com.growbiz.backend.repository.UserRepository;

@Controller
@RequestMapping(path = "/api")
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email,
            @RequestParam String password, @RequestParam String role) {
        Role userRole = new Role();
        User user = new User();

        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        switch (role) {
            case "admin":
                userRole = roleRepository.findByName(RoleType.ROLE_ADMIN);
                break;
            case "customer":
                userRole = roleRepository.findByName(RoleType.ROLE_CUSTOMER);
                break;
            case "business_owner":
                userRole = roleRepository.findByName(RoleType.ROLE_BUSINESS_OWNER);
                break;
        }
        user.setRole(userRole);

        userRepository.save(user);

        return "Saved successfully!";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
