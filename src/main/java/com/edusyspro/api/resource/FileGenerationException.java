package com.edusyspro.api.resource;

public class FileGenerationException extends Exception {
    public FileGenerationException(String message) {
        super(message);
    }

    public FileGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
