package com.Kshitiz.blog.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenHelper {

    // Token validity: 5 hours
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000;

    // 512-bit secret key (minimum required for HS512)
    private static final String SECRET =
            "fdjf83jfd9f3j9fj3jf9j3f9jf9JDFJ93jr9j3r9JDFO93489023r9j3r9fjsdf9";

    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    // ------------------------------------------------------------
    // 1. Generate JWT token
    // ------------------------------------------------------------
    public String generateToken(UserDetails userDetails) {

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // ------------------------------------------------------------
    // 2. Extract username from token
    // ------------------------------------------------------------
    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    // ------------------------------------------------------------
    // 3. Validate token
    // ------------------------------------------------------------
    public boolean validateToken(String token, UserDetails userDetails) {

        final String username = getUsernameFromToken(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // ------------------------------------------------------------
    // Helper: Check token expiration
    // ------------------------------------------------------------
    private boolean isTokenExpired(String token) {
        return getAllClaimsFromToken(token).getExpiration().before(new Date());
    }

    // ------------------------------------------------------------
    // Helper: Parse claims
    // ------------------------------------------------------------
    private Claims getAllClaimsFromToken(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
