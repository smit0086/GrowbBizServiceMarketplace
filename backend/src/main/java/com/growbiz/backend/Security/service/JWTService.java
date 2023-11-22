package com.growbiz.backend.Security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    private static String SECRET_KEY = "shdylvh2wNW6JpSW8EjdHhzzXabkFymh39mK4rznScgqXnN9BH2DgkaJfWj5Ko9";
    private static final long THOUSAND = 1000L;
    private static final long SIXTY = 60L;

    /**
     * Here we extract email i.e. the subject of the JWT
     *
     * @param token - token from where the email needs to be extracted
     * @return - email of the user
     */
    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Here we extract the expiry date of the JWT
     *
     * @param token - token from where the date needs to be extracted
     * @return - date of the token expiry
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Util method to extract claim from the JWT
     *
     * @param token          - token from where claim to be extracted
     * @param claimsResolver - tells what claim value needs to be extracted
     * @param <T>            - Type of claim
     * @return - the extracted claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims allClaims = extractAllClaims(token);
        return claimsResolver.apply(allClaims);
    }

    /**
     * Extract role from the JWT
     *
     * @param token - token from where the role needs to be extracted
     * @return - role of the user
     */
    public String extractRole(String token) {
        final Claims allClaims = extractAllClaims(token);
        return (String) allClaims.get("role");
    }

    /**
     * Extract all claims from the JWT
     *
     * @param token - token
     * @return - map of claims
     */

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    /**
     * Gets the key which is used to sign the token
     *
     * @return - Key
     */
    private Key getSignInKey() {
        byte[] keyByte = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }

    /**
     * Generate Token using only the userLogin details.
     *
     * @param username - email of the user
     * @param role     - role of the user
     * @return - JWT of the user
     */
    public String generateToken(String username, String role) {
        Map<String, String> claims = new HashMap<>();
        claims.put("role", role);
        return generateToken(claims, username);
    }

    /**
     * Generate Token using the extraClaims.
     * Token Subject - user email
     * Token Issued at - current timestamp
     * Token will expire after 1 hour
     * Token is signed with RSA 256 algorithm
     *
     * @param extraClaims - claims to be added in JWT. We are adding role of the user.
     * @param username    - email of the user
     * @return - JWT
     */
    public String generateToken(Map<String, String> extraClaims, String username) {
        return Jwts.builder().setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + THOUSAND * SIXTY * SIXTY))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Checks the validity of the token
     *
     * @param token     - token to be validated
     * @param userLogin - user details to check the username(email)
     * @return - if token is valid or not
     */
    public boolean isTokenValid(String token, UserDetails userLogin) {
        final String username = extractUserEmail(token);
        return (username.equals(userLogin.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks if the token is expired or not
     *
     * @param token - token to be validated
     * @return - if token is expired or not
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

}
