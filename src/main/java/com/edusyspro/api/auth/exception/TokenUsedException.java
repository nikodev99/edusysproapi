package com.edusyspro.api.auth.exception;

public class TokenUsedException extends RuntimeException {
    public TokenUsedException(String message) {
        super(message);
    }
}
