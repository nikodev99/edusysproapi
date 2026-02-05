package com.edusyspro.api.resource;

import com.edusyspro.api.auth.user.CustomUserDetails;
import com.edusyspro.api.model.School;

import java.io.ByteArrayOutputStream;

public interface FileGenerator<T> {
    void generate(CustomUserDetails user, T data, School school, ByteArrayOutputStream outputStream) throws FileGenerationException;
    String getMimeType();
    String getFileExtension();
    default boolean supports(Class<?> dataClass) {
        return false;
    }
}
