package com.example.product_catalog_api.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtTokenProvider {
    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);
    @Value("${app.jwt.secret}")
    private String jwtSecret;
    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;
    private SecretKey secretKey;

    @PostConstruct
    private void init() {
        byte[] decodedSecret = Decoders.BASE64.decode(jwtSecret);
        this.secretKey = Keys.hmacShaKeyFor(decodedSecret);
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String authoritiesString = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        Date issuedAt = new Date();
        Date expiryDate = new Date(issuedAt.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .subject(username)
                .issuedAt(issuedAt)
                .expiration(expiryDate)
                .claim("roles", authoritiesString)
                .signWith(this.secretKey, Jwts.SIG.HS512)
                .compact();
    }

    public boolean validateToken(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(secretKey)
                .build();

        try {
            Jws<Claims> claimsJws = parser.parseSignedClaims(token);
        } catch (JwtException ex) {
            log.error(ex.getMessage());
            return false;
        }

        return true;
    }

    public String getUsername(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(secretKey)
                .build();

        return parser.parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public List<SimpleGrantedAuthority> getRoles(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(secretKey)
                .build();

        return Arrays.stream(parser.parseSignedClaims(token)
                .getPayload()
                .get("roles", String.class)
                .split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}

