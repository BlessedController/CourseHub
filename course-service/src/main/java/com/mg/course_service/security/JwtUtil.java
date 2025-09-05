package com.mg.course_service.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mg.course_service.exception.TokenPayloadParsingException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtUtil {

    private final ObjectMapper mapper;

    public JwtUtil(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    //TODO: WHY IT WORKS WITHOUT SUBSTRING BEARER
    protected Map<String, Object> getClaimsFromToken(String token) {
        String[] tokenParts = token.split("\\.");

        if (tokenParts.length < 2) {
            throw new TokenPayloadParsingException("Invalid JWT format.");
        }

        String payload = tokenParts[1];

        String payloadJson = new String(Base64.getUrlDecoder().decode(payload), StandardCharsets.UTF_8);

        Map<String, Object> claims;

        try {
            claims = mapper.readValue(payloadJson, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new TokenPayloadParsingException(
                    "Failed to parse JWT payload JSON. Please check the token and try again."
            );
        }
        return claims;
    }

    public UUID getUserIdFromToken(String token) {

        Map<String, Object> claims = getClaimsFromToken(token);

        String stringUserId = (String) claims.get("userId");

        if (stringUserId == null) {
            throw new TokenPayloadParsingException("userId not found in token.");
        }

        return UUID.fromString(stringUserId);

    }

    public String getUsernameFromToken(String token) {

        Map<String, Object> claims = getClaimsFromToken(token);

        String username = (String) claims.get("sub");

        if (username == null) {
            throw new TokenPayloadParsingException("Username not found in token.");
        }

        return username;
    }

    public Boolean isAdmin(String token) {
        Map<String, Object> claims = getClaimsFromToken(token);

        Object role = claims.get("scope");

        if (role instanceof List<?> roles) {
            return roles.contains("ROLE_ADMIN");
        }

        return false;
    }

}
