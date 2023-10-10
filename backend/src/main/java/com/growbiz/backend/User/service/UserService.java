package com.growbiz.backend.User.service;

import com.growbiz.backend.User.models.Role;
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
    public User getUserByEmailAndRole(String email) {
        String[] arr = email.split(":");
        User user = iUserRepository.findByEmailAndRole(arr[0], Role.valueOf(arr[1]));
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("No User Name Available");
        }
        return user;
    }

    public void saveUser(User user) {
        iUserRepository.save(user);
    }

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByEmailAndRole(username);
    }
}
