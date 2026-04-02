package dev.belkin.notificator.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtTokenManager {

    private final long expirationTime;
    private final SecretKey key;

    public JwtTokenManager(
            @Value("${jwt.secret-key}") String keyString,
            @Value("${jwt.lifetime}") long expirationTime

    ) {
        this.key = Keys.hmacShaKeyFor(keyString.getBytes());
        this.expirationTime = expirationTime;

    }

    public Claims getAllClaims(String token) {

        return Jwts.parser().verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Integer getIdFromToken(String token) {

        String str = getAllClaims(token).get("id").toString();

        return Integer.parseInt(str);
    }

    public String getRoleFromToken(String token) {

        return getAllClaims(token).get("role", String.class);
    }
}

