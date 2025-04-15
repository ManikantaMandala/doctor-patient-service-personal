package org.hcltech.doctor_patient_appointment.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {

//    @Value("${jwtsecret}")
    private String SECRET_KEY = "lK3Px+gkZ5fZV7tvNLV6GdfJQvJ4lw4EjPe4C8x+lK4=e";

    public String extractUsernameFromToken(String token) {
        return extractClaimFromToken(token, Claims::getSubject);
    }

    public Date extractExpirationFromToken(String token) {
        return extractClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T extractClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaimFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaimFromToken(String token) {
        return Jwts.parser()
            .verifyWith(getSignKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    private Boolean isTokenExpired(String token) {
        return extractExpirationFromToken(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        final long now = System.currentTimeMillis();
        final long expireTime = now + 2 * 60 * 60 * 1_000;
        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date(now))
                .expiration(new Date(expireTime))
                .claims(claims)
                .signWith(getSignKey())
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
