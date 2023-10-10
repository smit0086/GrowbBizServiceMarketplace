package com.growbiz.backend.Security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
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


    private static String SECRET_KEY = "f524dc1dede396675482a80365275f77d39289f651cd47af037b3fc3ef6cbbb1";

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

    /**
     * Extract all claims from the JWT
     *
     * @param token
     * @return
     */

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSignInKey()).parseClaimsJws(token).getBody();
    }

    private Key getSignInKey() {
        byte[] keyByte = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }

    /**
     * Generate Token using only the userLogin details.
     *
     * @param userLogin
     * @return
     */
    public String generateToken(UserDetails userLogin) {
        return generateToken(new HashMap<>(), userLogin);
    }

    /**
     * Generate Token using the extraClaims.
     *
     * @param extraClaims
     * @param userLogin
     * @return
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
