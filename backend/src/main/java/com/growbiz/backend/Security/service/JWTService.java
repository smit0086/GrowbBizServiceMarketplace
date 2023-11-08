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

    /**
     * Here we extract email i.e. the subject of the JWT
     *
     * @param token
     * @return
     */
    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Here we extract email i.e. the subject of the JWT
     *
     * @param token
     * @return
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims allClaims = extractAllClaims(token);
        return claimsResolver.apply(allClaims);
    }

    public String extractRole(String token) {
        final Claims allClaims = extractAllClaims(token);
        return (String) allClaims.get("role");
    }

    /**
     * Extract all claims from the JWT
     *
     * @param token
     * @return
     */

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSignInKey() {
        byte[] keyByte = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }

    /**
     * Generate Token using only the userLogin details.
     *
     * @param username
     * @param role
     * @return
     */
    public String generateToken(String username, String role) {
        Map<String, String> claims = new HashMap<>();
        claims.put("role", role);
        return generateToken(claims, username);
    }

    /**
     * Generate Token using the extraClaims.
     *
     * @param extraClaims
     * @param username
     * @return
     */
    public String generateToken(Map<String, String> extraClaims, String username) {
        return Jwts.builder().setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean isTokenValid(String token, UserDetails userLogin) {
        final String username = extractUserEmail(token);
        return (username.equals(userLogin.getUsername())) && !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

}
