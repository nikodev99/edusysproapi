package com.edusyspro.api.auth.user;

import com.edusyspro.api.model.School;
import com.edusyspro.api.model.enums.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserSchoolRoleService {
    private final UserSchoolRoleRepository userSchoolRoleRepository;

    public UserSchoolRoleService(UserSchoolRoleRepository userSchoolRoleRepository) {
        this.userSchoolRoleRepository = userSchoolRoleRepository;
    }

    public List<UserSchoolRole> getActiveSchoolsForUser(Long userId) {
        return userSchoolRoleRepository.findByUserIdAndIsActiveTrue(userId);
    }

    public UserSchoolRole getUserSchoolRole(Long userId, String schoolId) {
        return userSchoolRoleRepository.findByUserIdAndSchoolIdAndIsActiveTrue(userId, UUID.fromString(schoolId));
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
