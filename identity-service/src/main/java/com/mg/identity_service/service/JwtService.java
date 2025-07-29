package com.mg.identity_service.service;

import com.mg.identity_service.dto.requests.LoginRequest;
import com.mg.identity_service.exception.UsernameOrPasswordMismatchException;
import com.mg.identity_service.model.User;
import com.mg.identity_service.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;


@Service
public class JwtService {
    private final UserRepository userRepository;
    private final Key key;


    public JwtService(UserRepository userRepository,
                      @Value("${services.identity.secret-key}") String secretKey) {
        this.userRepository = userRepository;
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String generateToken(LoginRequest request) {

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UsernameOrPasswordMismatchException("Username or password mismatch"));

        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("scope", List.of(user.getRole()));
        claims.put("userId", user.getId().toString());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
