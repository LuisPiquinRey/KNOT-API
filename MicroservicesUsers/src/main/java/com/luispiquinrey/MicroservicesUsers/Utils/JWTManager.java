package com.luispiquinrey.MicroservicesUsers.Utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JWTManager {

    private static final Logger log = LoggerFactory.getLogger(JWTManager.class);

    @Value("${spring.app.jwtSecret}")
    private String secretCode;

    private static final long EXPIRATION_TIME_MS = 10 * 60 * 1000;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities().iterator().next().getAuthority());

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
                .signWith(getKey())
                .compact();
    }

    public String getJwtFromHeader(HttpServletRequest request) throws IllegalAccessException {
        String header = request.getHeader("Authorization");
        log.info("Authorization header: {}", header);
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        } else {
            throw new IllegalAccessException("Access denied: missing or invalid Authorization header.");
        }
    }
    public String getUsernameFromJWT(String token){
        return Jwts.parser()
            .verifyWith((SecretKey)getKey())
            .build().parseSignedClaims(token)
            .getPayload().getSubject();
    }

    public boolean verifyTokenJwt(String token) {
        try {
            Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("JWT expired", e);
        } catch (UnsupportedJwtException e) {
            log.warn("Unsupported JWT", e);
        } catch (MalformedJwtException e) {
            log.warn("Malformed JWT", e);
        } catch (SecurityException e) {
            log.warn("Invalid JWT signature", e);
        } catch (IllegalArgumentException e) {
            log.warn("JWT token is null or empty", e);
        }
        return false;
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretCode));
    }
}
