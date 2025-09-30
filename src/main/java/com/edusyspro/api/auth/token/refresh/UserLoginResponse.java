package com.edusyspro.api.auth.token.refresh;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginResponse {
    private Long loginId;
    private Long accountId;
    private String clientIp;
    private Instant createdAt;
    private Instant lastUsedAt;
    private Instant expiryAt;
    private String device;
    private String browser;
    private String token;
}
