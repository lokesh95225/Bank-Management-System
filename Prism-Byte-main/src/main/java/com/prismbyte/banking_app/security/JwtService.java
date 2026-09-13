package com.prismbyte.banking_app.security;

import com.prismbyte.banking_app.config.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private final JwtProperties jwtProperties;
    private final SecretKey signingKey;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        byte[] keyBytes = jwtProperties.secret().matches("^[A-Za-z0-9+/=]+$")
                ? Decoders.BASE64.decode(jwtProperties.secret())
                : jwtProperties.secret().getBytes(StandardCharsets.UTF_8);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes.length >= 32 ? keyBytes : padKey(jwtProperties.secret()));
    }

    public String generateAccessToken(AppUserDetails userDetails) {
        return generateToken(userDetails, jwtProperties.accessTokenExpiration(), Map.of("type", "access"));
    }

    public String generateRefreshToken(AppUserDetails userDetails) {
        return generateToken(userDetails, jwtProperties.refreshTokenExpiration(), Map.of("type", "refresh"));
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, AppUserDetails userDetails, String expectedType) {
        Claims claims = extractClaims(token);
        return claims.getSubject().equals(userDetails.getUsername())
                && expectedType.equals(claims.get("type", String.class))
                && claims.getExpiration().after(new Date());
    }

    public long getAccessTokenExpiration() {
        return jwtProperties.accessTokenExpiration();
    }

    private String generateToken(AppUserDetails userDetails, long expirationMillis, Map<String, Object> claims) {
        Instant now = Instant.now();
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expirationMillis)))
                .signWith(signingKey)
                .compact();
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private byte[] padKey(String secret) {
        return String.format("%-32s", secret).replace(' ', '0').getBytes(StandardCharsets.UTF_8);
    }
}
