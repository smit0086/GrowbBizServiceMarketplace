package com.growbiz.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.growbiz.backend.Role.dto.RoleDTO;
import com.growbiz.backend.Role.dto.RoleType;
import com.growbiz.backend.Role.repository.RoleRepository;
import com.growbiz.backend.User.dto.UserDTO;
import com.growbiz.backend.User.repository.UserRepository;

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
        RoleDTO userRole = new RoleDTO();
        UserDTO user = new UserDTO();

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
    public @ResponseBody Iterable<UserDTO> getAllUsers() {
        return userRepository.findAll();
    }
}
