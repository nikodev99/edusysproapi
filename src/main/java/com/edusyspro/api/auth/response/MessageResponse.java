package com.edusyspro.api.auth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse {
    private String message;
    private String description;
    private String timestamp = Instant.now().toString();
    private Boolean isError = false;
}
