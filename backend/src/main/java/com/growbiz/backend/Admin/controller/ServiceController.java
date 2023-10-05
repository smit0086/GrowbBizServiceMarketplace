package com.growbiz.backend.Admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/service")
public class ServiceController {

    @GetMapping(path = "/get-services")
    public ResponseEntity<String> getServices() {

        return ResponseEntity.ok("Hello from the other side");
    }

    @PutMapping(path = "/add-services")
    public ResponseEntity<String> addServices() {

        return ResponseEntity.ok("Hello from the other side");
    }
}
