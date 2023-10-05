package com.growbiz.backend.User.repository;

import org.springframework.data.repository.CrudRepository;

import com.growbiz.backend.User.dto.UserDTO;

public interface UserRepository extends CrudRepository<UserDTO, Long> {
    
}
