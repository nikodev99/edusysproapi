package com.edusyspro.api.auth.user;

import com.edusyspro.api.model.enums.Role;
import com.edusyspro.api.utils.Datetime;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    private Long personalInfoId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserSchoolRole> schoolAffiliations = new ArrayList<>();

    @Enumerated
    private UserType userType;

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

    public List<Role> getRolesForSchool(UUID schoolId) {
        return schoolAffiliations.stream()
                .filter(affiliation -> affiliation.getSchool().getId().equals(schoolId) && affiliation.getIsActive())
                .findFirst()
                .map(UserSchoolRole::getRoles)
                .orElse(new ArrayList<>());
    }
}
