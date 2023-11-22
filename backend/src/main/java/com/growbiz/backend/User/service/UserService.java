package com.growbiz.backend.User.service;

import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.User.models.User;
import com.growbiz.backend.User.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserRepository iUserRepository;

    /**
     * @param email takes combination string as email:role
     * @return User fetched from the database
     */
    public User getUserByEmailAndRole(String email, String role) {
        return iUserRepository.findByEmailAndRole(email, Role.valueOf(role));
    }

    /**
     * Saves the user into the database
     *
     * @param user - user to be saved.
     */
    public void saveUser(User user) {
        iUserRepository.save(user);
    }

    /**
     * Locates the user based on the username.
     *
     * @param username the username identifying the user. Here it will be in the form "email:role"
     * @return a fully populated user record
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] arr = username.split(":");
        User user = getUserByEmailAndRole(arr[0], arr[1]);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("No Username exists with email: " + arr[0]);
        }
        return user;
    }
}
