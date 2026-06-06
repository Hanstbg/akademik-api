package org.delcom.app.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    private static final String SECRET_KEY = "NghR8fQn5O6V2z7VwpvQkDELCOMXoCYQbQZjx3xWUpPfw5i9L8RrGg==";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 2; // 2 jam
    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public static SecretKey getKey() { return key; }

    public static String generateToken(UUID userId, String role) {
        return Jwts.builder()
                .subject(userId.toString())
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public static UUID getUserIdFromToken(String token) {
        return extractUserId(token);
    }

    public static UUID extractUserId(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key).build()
                    .parseSignedClaims(token).getPayload();
            return UUID.fromString(claims.getSubject());
        } catch (Exception e) {
            return null;
        }
    }

    public static String extractRole(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key).build()
                    .parseSignedClaims(token).getPayload();
            return claims.get("role", String.class);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean validateToken(String token, boolean ignoreExpired) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            return ignoreExpired;
        } catch (Exception e) {
            return false;
        }
    }
}
