package com.edusyspro.api.auth.user;

import com.edusyspro.api.helper.RoleListConverter;
import com.edusyspro.api.helper.ZonedDatetimeConverter;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.enums.Role;
import com.edusyspro.api.utils.Datetime;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(unique = true, length = 50)
    private String email;

    @Column(unique = true, length = 15)
    private String phoneNumber;

    @Column(nullable = false, length = 120)
    private String password;

    private Boolean enabled = true;
    private Boolean accountNonLocked = true;
    private Integer failedLoginAttempts = 0;

    @Convert(converter = ZonedDatetimeConverter.class)
    private List<ZonedDateTime> lastLogin;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "personal_info_id", referencedColumnName = "id")
    private Individual personalInfo;

    @Enumerated(EnumType.STRING)
    @Convert(converter = RoleListConverter.class)
    private List<Role> roles;

    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = Datetime.brazzavilleDatetime();
        updatedAt = Datetime.brazzavilleDatetime();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Datetime.brazzavilleDatetime();
    }
}
