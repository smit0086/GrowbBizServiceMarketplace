package com.growbiz.backend.Security.config;

import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.Security.service.JWTAuthenticationFilter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
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

    @Autowired
    private final AuthenticationProvider authenticationProvider;

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
                        .requestMatchers(HttpMethod.GET, "/business/all").hasAnyAuthority(Role.ADMIN.name(), Role.PARTNER.name(), Role.CUSTOMER.name())
                        .requestMatchers("/admin/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/category/**").hasAnyAuthority(Role.ADMIN.name(), Role.PARTNER.name(), Role.CUSTOMER.name())
                        .requestMatchers("/booking/**").hasAnyAuthority(Role.ADMIN.name(), Role.PARTNER.name(), Role.CUSTOMER.name())
                        .requestMatchers(HttpMethod.GET, "/services/**").hasAnyAuthority(Role.ADMIN.name(), Role.PARTNER.name(), Role.CUSTOMER.name())
                        .requestMatchers("/services/**").hasAnyAuthority(Role.ADMIN.name(), Role.PARTNER.name())
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/payment/webhook").permitAll()
                        .requestMatchers(HttpMethod.GET, "/reviews/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/reviews/**").hasAnyAuthority(Role.CUSTOMER.name())
                        .requestMatchers(HttpMethod.DELETE, "/reviews/deleteReviewAndRating").hasAnyAuthority(Role.CUSTOMER.name())
                        .anyRequest().authenticated()
                ).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).build();
    }
}
