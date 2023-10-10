package com.growbiz.backend.Admin.controller;

import com.growbiz.backend.Admin.service.IAdminService;
import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
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
    IAdminService adminService;

    @PostMapping(path = "/addCategory")
    public ResponseEntity<AuthenticationResponse> addCategory(@RequestBody Category newCategory) {
        AuthenticationResponse response = adminService.addCategory(newCategory);

        if (response.getToken() == "Pass") {
            return ResponseEntity.ok(response);
        } else {
            return (ResponseEntity<AuthenticationResponse>) ResponseEntity.badRequest();
        }
    }

    @PostMapping(path = "/updateCategory")
    public ResponseEntity<AuthenticationResponse> updateCategory(@RequestBody Category newCategory) {
        AuthenticationResponse response = adminService.updateCategory(newCategory);

        if (response.getToken() == "Pass") {
            return ResponseEntity.ok(response);
        } else {
            return (ResponseEntity<AuthenticationResponse>) ResponseEntity.badRequest();
        }
    }
    @PostMapping(path = "/deleteCategory")
    public ResponseEntity<AuthenticationResponse> deleteCategory(@RequestBody Category category) {
        AuthenticationResponse response = adminService.deleteCategory(category);

        if (response.getToken() == "Pass") {
            return ResponseEntity.ok(response);
        } else {
            return (ResponseEntity<AuthenticationResponse>) ResponseEntity.badRequest();
        }
    }

    @PostMapping(path = "/addSubCategory")
    public ResponseEntity<AuthenticationResponse> addSubCategory(@RequestBody SubCategory newCategory) {
        AuthenticationResponse response = adminService.addSubCategory(newCategory);

        if (response.getToken() == "Pass") {
            return ResponseEntity.ok(response);
        } else {
            return (ResponseEntity<AuthenticationResponse>) ResponseEntity.badRequest();
        }
    }

    @PostMapping(path = "/updateSubCategory")
    public ResponseEntity<AuthenticationResponse> updateSubCategory(@RequestBody SubCategory newCategory) {
        AuthenticationResponse response = adminService.updateSubCategory(newCategory);

        if (response.getToken() == "Pass") {
            return ResponseEntity.ok(response);
        } else {
            return (ResponseEntity<AuthenticationResponse>) ResponseEntity.badRequest();
        }
    }
    @PostMapping(path = "/deleteSubCategory")
    public ResponseEntity<AuthenticationResponse> deleteSubCategory(@RequestBody SubCategory subCategory) {
        AuthenticationResponse response = adminService.deleteSubCategory(subCategory);

        if (response.getToken() == "Pass") {
            return ResponseEntity.ok(response);
        } else {
            return (ResponseEntity<AuthenticationResponse>) ResponseEntity.badRequest();
        }
    }

}
