package com.edusyspro.api.auth.response;

import com.edusyspro.api.auth.user.UserType;
import com.edusyspro.api.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("account")
    private Long accountId;
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
