package com.growbiz.backend.Security.config;

import com.growbiz.backend.User.models.User;
import com.growbiz.backend.User.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class BasicAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private final IUserService userService;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Bean
    public UserDetailsService userDetailsService() {
        return userService::getUserByEmailAndRole;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        
        String email = authentication.getName();
        String password = (String) authentication.getCredentials();

        User user = userService.getUserByEmailAndRole(email);

        if (Objects.isNull(user)) {
            throw new BadCredentialsException("Username not found.");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }

        return new UsernamePasswordAuthenticationToken(email, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }
}