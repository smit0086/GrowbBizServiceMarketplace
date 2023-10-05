package com.growbiz.backend.Admin.model;

import com.growbiz.backend.Admin.Services.model.ServiceDto;

import java.util.List;

public class AdminDto {

    private String adminEmail;
    private String password;
    private List<ServiceDto> services;

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminName(String adminName) {
        this.adminEmail = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ServiceDto> getServices() {
        return services;
    }

    public void setServices(List<ServiceDto> services) {
        this.services = services;
    }
}
