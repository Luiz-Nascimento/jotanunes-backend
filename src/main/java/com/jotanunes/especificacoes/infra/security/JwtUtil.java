package com.jotanunes.especificacoes.infra.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long expiration;

    public JwtUtil(@Value("${JWT_SECRET}") String secret,
                   @Value("${JWT_EXPIRATION}") long expiration) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("role", userDetails.getAuthorities().iterator().next().getAuthority())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    public String getUserNameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    public String getClaimFromToken(String token, String claimKey) {
        Claims claims = parseToken(token);
        return claims.get(claimKey, String.class);
    }

    public boolean isTokenExpired(String token) {
        Claims claims = parseToken(token);
        Date expirationDate = claims.getExpiration();
        return expirationDate.before(new Date());
    }


    private Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }


}
