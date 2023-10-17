package com.growbiz.backend.Security.config;

import com.growbiz.backend.Security.service.JWTAuthenticationFilter;
import com.growbiz.backend.User.models.Role;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@Slf4j
@AllArgsConstructor
public class SecurityConfiguration {

    @Autowired
    private final JWTAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.cors(request -> new CorsConfiguration().addAllowedOrigin("*"))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/business/").hasAuthority(Role.PARTNER.name())
                        .requestMatchers(HttpMethod.GET, "/business/download").hasAnyAuthority(Role.PARTNER.name(), Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/business/save").hasAuthority(Role.PARTNER.name())
                        .requestMatchers(HttpMethod.PUT, "/{businessId}").hasAuthority(Role.PARTNER.name())
                        .requestMatchers(HttpMethod.PUT, "/{businessId}/verify").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/business/all").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/admin/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/business/businessHours").hasAnyAuthority(Role.PARTNER.name())
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/admin/**").permitAll()
                        .anyRequest().authenticated()
                ).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).build();
    }
}
