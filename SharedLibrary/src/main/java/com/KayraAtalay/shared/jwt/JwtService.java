package com.KayraAtalay.shared.jwt;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    public static final String SECRET_KEY = "/gL7+WzWy/GRbIKlBw7zKqgMRFxu04dBpA1T+A9vj6U=";


    public String generateToken(UserDetails userDetails) {

        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(item -> item.getAuthority())
                .orElse("USER");

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2)) // 2 saat
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public <T> T getClaim(String token, Function<Claims, T> claimsFunc) {
        Claims claims = getClaims(token);
        return claimsFunc.apply(claims);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public String getUsernameByToken(String token) {
        return getClaim(token, Claims::getSubject);
    }


    public String getRoleByToken(String token) {
        return getClaim(token, claims -> claims.get("role", String.class));
    }

    public boolean isTokenValid(String token) {
        try {
            Date expireDate = getClaim(token, Claims::getExpiration);
            return new Date().before(expireDate);
        } catch (Exception e) {
            return false;
        }
    }

    private Key getKey() {
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }
}