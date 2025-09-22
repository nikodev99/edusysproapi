package com.edusyspro.api.auth.user;

import com.edusyspro.api.dto.custom.SchoolBasic;
import com.edusyspro.api.model.enums.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {
    @Getter
    private final Long id;
    private final String username;

    @Getter
    private final String email;

    @Getter
    private final String phoneNumber;
    private final String password;

    @Getter
    private final Long personalInfoId;

    @Getter
    private final UserType userType;

    List<UserSchoolRole> schoolAffiliations;

    private UUID currentSchoolId;
    private List<Role> currentSchoolRoles;

    private final Collection<? extends GrantedAuthority> authorities;

    protected CustomUserDetails (
            Long id, String username, String email, String phoneNumber, String password, Long personalInfoId,
            UserType userType, List<UserSchoolRole> schoolAffiliations, Collection<? extends GrantedAuthority> authorities
    ) {
        this.id = id;
        this.personalInfoId = personalInfoId;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userType = userType;
        this.schoolAffiliations = schoolAffiliations != null ? schoolAffiliations : new ArrayList<>();
        this.authorities = authorities;
    }

    public static CustomUserDetails build(User user) {
        List<GrantedAuthority> authorities = user.getSchoolAffiliations().stream()
                .filter(UserSchoolRole::getIsActive)
                .flatMap(affiliation -> affiliation.getRoles().stream())
                .distinct()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toUnmodifiableList());

        return new CustomUserDetails(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getPassword(),
                user.getPersonalInfoId(),
                user.getUserType(),
                user.getSchoolAffiliations(),
                authorities
        );
    }

    public void setCurrentSchoolId(UUID schoolId) {
        this.currentSchoolId = schoolId;
        this.currentSchoolRoles = schoolAffiliations.stream()
                .filter(affiliation -> affiliation.getSchool().getId().equals(currentSchoolId))
                .findFirst()
                .map(UserSchoolRole::getRoles)
                .orElse(new ArrayList<>());
    }

    public List<SchoolBasic> getAvailableSchools() {
        return schoolAffiliations.stream()
                .filter(UserSchoolRole::getIsActive)
                .map(affiliation -> new SchoolBasic(
                        affiliation.getSchool().getId(),
                        affiliation.getSchool().getName(),
                        affiliation.getSchool().getAbbr(),
                        affiliation.getSchool().getWebsiteURL()
                ))
                .toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (currentSchoolRoles != null && !currentSchoolRoles.isEmpty()) {
            return currentSchoolRoles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.name()))
                    .collect(Collectors.toList());
        }
        return schoolAffiliations.stream()
                .filter(UserSchoolRole::getIsActive)
                .flatMap(affiliation -> affiliation.getRoles().stream())
                .distinct()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return Optional.ofNullable(schoolAffiliations)
                .orElse(Collections.emptyList())
                .stream()
                .anyMatch(UserSchoolRole::getAccountNonLocked);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Optional.ofNullable(schoolAffiliations)
                .orElse(Collections.emptyList())
                .stream()
                .anyMatch(UserSchoolRole::getIsActive);
    }

    @Override
    public boolean isEnabled() {
        return Optional.ofNullable(schoolAffiliations)
                .orElse(Collections.emptyList())
                .stream()
                .anyMatch(UserSchoolRole::getEnabled);
    }


}
