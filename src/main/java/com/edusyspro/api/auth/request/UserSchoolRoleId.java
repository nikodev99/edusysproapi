package com.edusyspro.api.auth.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSchoolRoleId {
    private Long userId;
    private UUID schoolId;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserSchoolRoleId that = (UserSchoolRoleId) obj;
        return Objects.equals(userId, that.userId) && Objects.equals(schoolId, that.schoolId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, schoolId);
    }
}
