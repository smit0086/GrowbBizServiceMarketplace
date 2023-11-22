package com.growbiz.backend.User.service;

import com.growbiz.backend.User.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface IUserService extends UserDetailsService {
    
    /**
     * @param email takes combination string as email:role
     * @return User fetched from the database
     */
    public User getUserByEmailAndRole(String email, String role);

    /**
     * Saves the user into the database
     *
     * @param user - user to be saved.
     */
    public void saveUser(User user);
}
