package com.growbiz.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/api")
public class MainController {
//    @Autowired
//    private ICustomerRepository ICustomerRepository;
//
//    @PostMapping(path = "/add")
//    public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email,
//                                           @RequestParam String password, @RequestParam String role) {
//
//        UserDTO user = new UserDTO();
//
//        user.setName(name);
//        user.setEmail(email);
//        user.setPassword(password);
//
//        switch (role) {
//            case "admin":
//                userRole = roleRepository.findByName(RoleType.ROLE_ADMIN);
//                break;
//            case "customer":
//                userRole = roleRepository.findByName(RoleType.ROLE_CUSTOMER);
//                break;
//            case "business_owner":
//                userRole = roleRepository.findByName(RoleType.ROLE_BUSINESS_OWNER);
//                break;
//        }
//        user.setRole(userRole);
//
//        ICustomerRepository.save(user);
//
//        return "Saved successfully!";
//    }
//
//    @GetMapping(path = "/all")
//    public @ResponseBody Iterable<UserDTO> getAllUsers() {
//        return ICustomerRepository.findAll();
//    }
}
