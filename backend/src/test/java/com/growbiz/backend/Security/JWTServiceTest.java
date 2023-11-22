//package com.growbiz.backend.Security;
//
//import com.growbiz.backend.Security.service.JWTService;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.security.Key;
//import java.util.Base64;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class JWTServiceTest {
//
//    private static final long EXPIRATION_TIME = 1000 * 60 * 24 * 24;
//
//    private static final String SECRET_KEY = "shdylvh2wNW6JpSW8EjdHhzzXabkFymh39mK4rznScgqXnN9BH2DgkaJfWj5Ko9";
//
//    private static final String TEST_USER_EMAIL = "test@dal.ca";
//    private static final String TEST_USER_ROLE = "CUSTOMER";
//
//    @InjectMocks
//    private JWTService mockJwtService;
//
//
//    @BeforeEach
//    public void init() {
//
//
//    }
//
//    private static Key getSignInKey() {
//        byte[] keyByte = Base64.getDecoder().decode(SECRET_KEY);
//        return Keys.hmacShaKeyFor(keyByte);
//    }
//
//    public static String createToken(String username, Map<String, String> claims) {
//        String jwt = Jwts.builder().setClaims(claims)
//                .setSubject(username)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                .compact();
//
//        return jwt;
//    }
//
//    public static String createToken(String username) {
//        String jwt = Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                .compact();
//
//        return jwt;
//    }
//
//    @Test
//    public void checkIsTokenValidTestSuccess() {
//        boolean actualIsExpired;
//        String token = createToken(TEST_USER_EMAIL);
//        UserDetails mockUserDetail = mock(UserDetails.class);
//
//        when(mockUserDetail.getUsername()).thenReturn(TEST_USER_EMAIL);
//        actualIsExpired = mockJwtService.isTokenValid(token, mockUserDetail);
//        assertTrue(actualIsExpired);
//    }
//
//    @Test
//    public void generateTokenWithUserAndRoleTest() {
//        String token = mockJwtService.generateToken(TEST_USER_EMAIL, TEST_USER_ROLE);
//
//        assertNotNull(token);
//    }
//
//    @Test
//    public void extractRoleFromTokenTest() {
//        Map<String, String> claims = new HashMap<>();
//        String token;
//        String actualRole;
//
//        claims.put("role", TEST_USER_ROLE);
//        token = createToken(TEST_USER_EMAIL, claims);
//        actualRole = mockJwtService.extractRole(token);
//
//        assertEquals(TEST_USER_ROLE, actualRole);
//    }
//}
