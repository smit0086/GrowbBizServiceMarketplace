package com.growbiz.backend.repository;

import org.springframework.data.repository.CrudRepository;

import com.growbiz.backend.models.Role;
import com.growbiz.backend.models.RoleType;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByName(RoleType role);
}
