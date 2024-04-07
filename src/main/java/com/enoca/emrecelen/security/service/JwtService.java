package com.enoca.emrecelen.security.service;

import com.enoca.emrecelen.model.entity.Customer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpHeaders;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private String SECRET_KEY;

    private Key getKey() {
        byte[] key = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Customer customer) {
        return Jwts.builder()
                .claims(new HashMap<>())
                .header()
                .add("typ", "JWT")
                .and()
                .subject(customer.getEmail())
                .claim("role", customer.getRole().name())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 10000 * 60 * 24))
                .signWith(SignatureAlgorithm.HS384, getKey())
                .compact();
    }

    private <T> T exportToken(String token, Function<Claims, T> claimsTFunction) {
        final JwtParser jwtParser = Jwts.parser().setSigningKey(getKey()).build();
        final Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return claimsTFunction.apply(claims);
    }

    private String getHeadersToken(HttpHeaders headers) {
        return headers.get(HttpHeaders.AUTHORIZATION).get(0).substring(7);
    }

    public String findByCustomerEmail(HttpHeaders headers) {
        return exportToken(getHeadersToken(headers), Claims::getSubject);
    }

    public String findByCustomerEmail(String token) {
        return exportToken(token, Claims::getSubject);
    }
}
