package com.edusyspro.api.auth.request;

import com.edusyspro.api.auth.user.UserType;
import com.edusyspro.api.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoResponse {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private List<Role> roles;
    private String phoneNumber;
    private Boolean enabled;
    private Boolean accountNonLocked;
    private Integer failedLoginAttempts;
    private ZonedDateTime lastLogin;
    private UserType userType;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
