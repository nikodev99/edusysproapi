package com.edusyspro.api.auth.request;

import com.edusyspro.api.auth.user.User;
import com.edusyspro.api.auth.user.UserSchoolRole;
import com.edusyspro.api.model.School;
import com.edusyspro.api.model.enums.Role;

import java.util.List;
import java.util.UUID;

public record AssignToUserRequest(
        Long userId,
        String schoolId,
        List<Role> roles
) {
    public UserSchoolRole affiliation() {
        return UserSchoolRole.builder()
                .user(User.builder()
                        .id(userId)
                        .build())
                .school(School.builder()
                        .id(UUID.fromString(schoolId))
                        .build())
                .roles(roles)
                .isActive(true)
                .build();
    }
}
