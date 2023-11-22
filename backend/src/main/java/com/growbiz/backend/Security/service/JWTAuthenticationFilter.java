package com.growbiz.backend.Security.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;


@Slf4j
@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private final JWTService jwtService;

    @Autowired
    private final UserDetailsService userDetailsService;
    private static final int SEVEN = 7;

    /**
     * This method evaluates and validates the JWT and set it in the SecurityContext.
     *
     * @param request     - HTTP Request
     * @param response    - HTTP Response
     * @param filterChain - filterChain
     * @throws ServletException - throws ServletException
     * @throws IOException      - throws IOException
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        logger.info("Entering JWTAuthenticationFilter.doFilterInternal()");
        final String authenticationHeader = request.getHeader("Authorization");

        if (Objects.isNull(authenticationHeader) || !authenticationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String token = authenticationHeader.substring(SEVEN);
        // Extracted userEmail from JWT
        final String userEmail = jwtService.extractUserEmail(token);
        final String userRole = jwtService.extractRole(token);

        if (Objects.nonNull(userEmail) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail + ":" + userRole);

            if (jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }
        filterChain.doFilter(request, response);
        logger.info("Exiting from JWTAuthenticationFilter.doFilterInternal()");
    }
}
