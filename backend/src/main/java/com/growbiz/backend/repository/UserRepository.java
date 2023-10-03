package com.growbiz.backend.repository;

import org.springframework.data.repository.CrudRepository;

import com.growbiz.backend.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
    
}
