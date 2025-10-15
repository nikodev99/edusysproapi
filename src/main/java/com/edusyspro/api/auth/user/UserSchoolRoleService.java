package com.edusyspro.api.auth.user;

import com.edusyspro.api.auth.request.AssignToUserRequest;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.model.School;
import com.edusyspro.api.model.enums.Role;
import com.edusyspro.api.utils.Datetime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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

    public Boolean isUserSchoolRoleExist(Long userId, String schoolId) {
        Optional<UserSchoolRole> userSchoolRole = userSchoolRoleRepository.findByUserIdAndSchoolId(userId, UUID.fromString(schoolId));
        return userSchoolRole.isPresent();
    }

    @Transactional
    public void updateLastLogin(Long userId, UUID schoolId) {
        userSchoolRoleRepository.findByUserIdAndSchoolId(userId, schoolId)
                .ifPresent(
                        user -> userSchoolRoleRepository.updateLastLogin(user.getId(), user.getSchoolId(), Datetime.brazzavilleDatetime())
                );
    }

    @Transactional
    public boolean updateAccount(long accountId, UpdateField field) {
        int updated = userAccountUpdate.updateUserAccountField(field.field(),  field.value(), accountId);
        return updated > 0;
    }

    public void assignUserToSchool(AssignToUserRequest request) {
        if (request.affiliation() == null) {
            throw new IllegalArgumentException("Affiliation cannot be null");
        }
        userSchoolRoleRepository.save(request.affiliation());
    }
}
