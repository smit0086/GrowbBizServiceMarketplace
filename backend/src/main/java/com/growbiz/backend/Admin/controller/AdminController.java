package com.growbiz.backend.Admin.controller;

import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.service.Super.ICategoryService;
import com.growbiz.backend.UserAuthentication.model.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    ICategoryService categoryService;
/**
    @PostMapping(path = "/addCategory")
    public ResponseEntity<AuthenticationResponse> addCategory(@RequestBody Category newCategory) {
        return ResponseEntity.ok();
    }

    @PostMapping(path = "/updateCategory")
    public ResponseEntity<AuthenticationResponse> updateCategory(@RequestBody Category authRequest) {
        return ResponseEntity.ok();
    }
    @PostMapping(path = "/deleteCategory")
    public ResponseEntity<AuthenticationResponse> deleteCategory(@RequestBody Category authRequest) {
        return ResponseEntity.ok();
    }
**/
}
