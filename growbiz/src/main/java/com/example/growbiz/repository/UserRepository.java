package com.example.growbiz.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.growbiz.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
    
}
