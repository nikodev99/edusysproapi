package com.edusyspro.api.auth.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant expiryDate;

    @Column(nullable = false)
    private boolean used = false;

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
        expiryDate = createdAt.plus(1, ChronoUnit.HOURS);
    }
}
