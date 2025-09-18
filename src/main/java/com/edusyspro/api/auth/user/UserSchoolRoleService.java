package com.edusyspro.api.auth.user;

import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.model.School;
import com.edusyspro.api.model.enums.Role;
import com.edusyspro.api.utils.Datetime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserSchoolRoleService {
    private final UserSchoolRoleRepository userSchoolRoleRepository;
    private final UserAccountUpdate userAccountUpdate;

    public UserSchoolRoleService(
            UserSchoolRoleRepository userSchoolRoleRepository,
            UserAccountUpdate userAccountUpdate
    ) {
        this.userSchoolRoleRepository = userSchoolRoleRepository;
        this.userAccountUpdate = userAccountUpdate;
    }

    public List<UserSchoolRole> getActiveSchoolsForUser(Long userId) {
        return userSchoolRoleRepository.findByUserIdAndIsActiveTrue(userId);
    }

    public UserSchoolRole getUserSchoolRole(Long userId, String schoolId) {
        return userSchoolRoleRepository.findByUserIdAndSchoolIdAndIsActiveTrue(userId, UUID.fromString(schoolId));
    }

    @Transactional
    public void updateLastLogin(Long userId, UUID schoolId) {
        userSchoolRoleRepository.findByUserIdAndSchoolId(userId, schoolId)
                .ifPresent(
                        user -> userSchoolRoleRepository.updateLastLogin(user.getId(), user.getSchoolId(), Datetime.brazzavilleDatetime())
                );
    }

    public boolean updateAccount(long accountId, UpdateField field) {
        int updated = userAccountUpdate.updateUserAccountField(field.field(),  field.value(), accountId);
        return updated > 0;
    }

    public void assignUserToSchool(Long userId, String schoolId, List<Role> roles) {
        UserSchoolRole affiliation = UserSchoolRole.builder()
                .user(User.builder()
                        .id(userId)
                        .build())
                .school(School.builder()
                        .id(UUID.fromString(schoolId))
                        .build())
                .roles(roles)
                .isActive(true)
                .build();
        userSchoolRoleRepository.save(affiliation);
    }
}
