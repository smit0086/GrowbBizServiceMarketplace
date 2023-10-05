package com.growbiz.backend.Admin.model;

import com.growbiz.Backend.Admin.Services.model.ServiceDto;

import java.util.List;

public class AdminDto {
    private Long adminID;
    private String adminName;
    private String password;
    private List<ServiceDto> services;

    public Long getId() {
        return adminID;
    }

    public void setId(Long id) {
        this.adminID = id;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
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
