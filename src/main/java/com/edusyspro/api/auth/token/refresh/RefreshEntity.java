package com.edusyspro.api.auth.token.refresh;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"refresh_token"})
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String refreshToken;

    private String userAgent;

    @Column(length = 45)
    private String clientIp;

    @Column(nullable = false)
    private Instant expiryDate;

    @Column(nullable = false)
    private Instant createdAt;

    private Instant lastUsedAt;

    private Instant revokedAt;

    @Column(nullable = false)
    private boolean isActive;

    private String deviceFingerprint; // For more sophisticated device tracking

    private Integer refreshCount = 0;

    private String clientType;
}
