package com.coursehub.media_stock_service.util;

import com.coursehub.media_stock_service.exception.TokenPayloadParsingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Component
public class JwtUtil {

    private final ObjectMapper mapper;

    public JwtUtil(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public String getUsernameFromToken(String token) {

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

        String username = (String) claims.get("sub");


        if (username == null) {
            throw new TokenPayloadParsingException("userId not found in token.");
        }

        return username;
    }

}
