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
public class UserActivityResponse {
    private Long id;
    private String action;
    private Instant actionDate;
    private String ipAddress;
    private String description;
}
