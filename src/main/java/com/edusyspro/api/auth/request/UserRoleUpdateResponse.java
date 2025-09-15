package com.edusyspro.api.auth.request;

import com.edusyspro.api.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleUpdateResponse {
    private Long userId;
    private String schoolId;
    private List<Role> oldRoles;
    private List<Role> newRoles;
    private String message;
    private String timestamp;
    private boolean success;

    public static UserRoleUpdateResponse success(Long userId, String schoolId, List<Role> oldRoles, List<Role> newRoles) {
        return UserRoleUpdateResponse.builder()
                .userId(userId)
                .schoolId(schoolId)
                .oldRoles(oldRoles)
                .newRoles(newRoles)
                .message("User roles updated successfully")
                .timestamp(Instant.now().toString())
                .success(true)
                .build();
    }

    public static UserRoleUpdateResponse failure(Long userId, String schoolId, String message) {
        return UserRoleUpdateResponse.builder()
                .userId(userId)
                .schoolId(schoolId)
                .message(message)
                .timestamp(Instant.now().toString())
                .success(false)
                .build();
    }
}
