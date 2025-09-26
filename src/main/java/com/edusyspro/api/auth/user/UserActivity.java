package com.edusyspro.api.auth.user;

import com.edusyspro.api.utils.Datetime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String action;
    private Instant actionDate;
    private String ipAddress;
    private String description;

    @Column(name = "account_id")
    private long accountId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "account_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserSchoolRole account;

    @PrePersist
    public void prePersist() {
        actionDate = Datetime.brazzavilleDatetime().toInstant();
    }
}
