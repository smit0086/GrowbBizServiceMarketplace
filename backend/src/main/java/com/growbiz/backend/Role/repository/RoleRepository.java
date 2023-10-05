package com.growbiz.backend.Role.repository;

import org.springframework.data.repository.CrudRepository;

import com.growbiz.backend.Role.dto.RoleDTO;
import com.growbiz.backend.Role.dto.RoleType;

public interface RoleRepository extends CrudRepository<RoleDTO, Integer> {
    RoleDTO findByName(RoleType role);
}
