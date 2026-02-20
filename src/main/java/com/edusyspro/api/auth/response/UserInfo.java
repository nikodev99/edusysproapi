package com.edusyspro.api.auth.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserInfo(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName
) {
    public UserInfoResponse toResponse() {
        return UserInfoResponse.builder()
                .id(id)
                .username(username)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }
}
