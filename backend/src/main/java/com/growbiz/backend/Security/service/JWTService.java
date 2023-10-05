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

    // TODO - move it to yaml config
    private static final String SECRET_KEY = "26e3ed5988bc73d53036ea4f729f28d0ddb2e6cf30af0ef7914a528bdd6f3692";

    /**
     * Here we extract email i.e. the subject of the JWT
     *
     * @param token - token
     * @return - return String
     */
    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Here we extract email i.e. the subject of the JWT
     *
     * @param token - token
     * @return - return Date
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims allClaims = extractAllClaims(token);
        return claimsResolver.apply(allClaims);
    }

    /**
     * Extract all claims from the JWT
     *
     * @param token - token
     * @return - return Claims
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
     * @param userLogin - userLogin
     * @return - return String
     */
    public String generateToken(UserDetails userLogin) {
        return generateToken(new HashMap<>(), userLogin);
    }

    /**
     * Generate Token using the extraClaims.
     *
     * @param extraClaims - extraClaims
     * @param userLogin   - userLogin
     * @return - return String
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userLogin) {
        return Jwts.builder().setClaims(extraClaims)
                .setSubject(userLogin.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
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
