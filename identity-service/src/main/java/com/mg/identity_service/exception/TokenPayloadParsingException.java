package com.mg.identity_service.exception;

public class TokenPayloadParsingException extends RuntimeException {
    public TokenPayloadParsingException(String message) {
        super(message);
    }
}
