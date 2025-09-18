package com.edusyspro.api.auth.user;

import com.edusyspro.api.helper.RoleListConverter;
import com.edusyspro.api.model.School;
import com.edusyspro.api.model.enums.Role;
import com.edusyspro.api.utils.Datetime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_school_roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSchoolRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "school_id")
    private UUID schoolId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "school_id", referencedColumnName = "id", insertable = false, updatable = false)
    private School school;

    @Enumerated(EnumType.STRING)
    @Convert(converter = RoleListConverter.class)
    private List<Role> roles;

    private Boolean isActive = true;
    private Boolean enabled = true;
    private Boolean accountNonLocked = true;
    private Integer failedLoginAttempts = 0;

    private ZonedDateTime lastLogin;

    private ZonedDateTime assignedAt;
    private ZonedDateTime lastModifyAt;

    @PrePersist
    public void prePersist() {
        enabled = true;
        accountNonLocked = true;
        failedLoginAttempts = 0;
        isActive = true;
        assignedAt = Datetime.brazzavilleDatetime();

        if (userId == null && user != null) {
            userId = user.getId();
        }
    }

    @PreUpdate
    public void preUpdate() {
        lastModifyAt = Datetime.brazzavilleDatetime();
    }

}
