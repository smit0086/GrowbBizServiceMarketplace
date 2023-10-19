package com.growbiz.backend.User.repository;

import com.growbiz.backend.User.models.Role;
import com.growbiz.backend.User.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends CrudRepository<User, Long> {
    public User findByEmailAndRole(String email, Role role);
}
