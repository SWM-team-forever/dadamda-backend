package com.forever.dadamda.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

@Service
@Slf4j
public class TokenService {

    private Key secretKey;

    @Value("${security.jwt.token.secret-key}")
    private String SECRET_KEY;

    @Value("${security.jwt.token.expire-length}")
    private Long EXPIRE_LENGTH;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    public String generateToken(String email, String role) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);

        return Jwts.builder().setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_LENGTH))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey)
                .build().parseClaimsJws(token);

        return claims.getBody().getExpiration()
                .after(new Date(System.currentTimeMillis()));
    }

    public String getEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build().parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }
}