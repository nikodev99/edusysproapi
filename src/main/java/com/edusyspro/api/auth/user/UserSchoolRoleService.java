package com.edusyspro.api.auth.user;

import com.edusyspro.api.auth.request.UserRoleUpdateResponse;
import com.edusyspro.api.exception.sql.NotFountException;
import com.edusyspro.api.helper.log.L;
import com.edusyspro.api.model.School;
import com.edusyspro.api.model.enums.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    // ... existing code ...

    @Transactional
    public UserRoleUpdateResponse updateUserRolesWithDetails(Long userId, String schoolId, List<Role> newRoles) {
        try {
            UUID schoolUuid = UUID.fromString(schoolId);

            // Validate user exists
            User user = userRepository.findUserById(userId)
                    .orElseThrow(() -> new NotFountException("User not found with id: " + userId));

            // Check if user has affiliation with the school
            Optional<UserSchoolRole> userSchoolRoleOpt = userSchoolRoleRepository
                    .findByUserIdAndSchoolId(userId, schoolUuid);

            if (userSchoolRoleOpt.isEmpty()) {
                L.warn("User {} has no affiliation with school {}", userId, schoolId);
                return UserRoleUpdateResponse.failure(userId, schoolId,
                        "User has no affiliation with the specified school");
            }

            UserSchoolRole userSchoolRole = userSchoolRoleOpt.get();
            List<Role> oldRoles = new ArrayList<>(userSchoolRole.getRoles());

            // If the affiliation is inactive, activate it
            if (!userSchoolRole.getIsActive()) {
                userSchoolRole.setIsActive(true);
                userSchoolRoleRepository.save(userSchoolRole);
            }

            // Update roles
            int updatedRows = userSchoolRoleRepository.updateUserRole(userId, schoolUuid, newRoles);

            if (updatedRows > 0) {
                L.info("Successfully updated roles for user {} in school {} from {} to {}",
                        userId, schoolId, oldRoles, newRoles);
                return UserRoleUpdateResponse.success(userId, schoolId, oldRoles, newRoles);
            } else {
                L.warn("No rows updated when changing roles for user {} in school {}",
                        userId, schoolId);
                return UserRoleUpdateResponse.failure(userId, schoolId,
                        "No changes were made to user roles");
            }

        } catch (IllegalArgumentException e) {
            L.error("Invalid school ID format: {}", schoolId, e);
            return UserRoleUpdateResponse.failure(userId, schoolId,
                    "Invalid school ID format");
        } catch (NotFountException e) {
            L.error("User not found: {}", e.getMessage());
            return UserRoleUpdateResponse.failure(userId, schoolId, e.getMessage());
        } catch (Exception e) {
            L.error("Error updating user roles for user {} in school {}: {}",
                    userId, schoolId, e.getMessage(), e);
            return UserRoleUpdateResponse.failure(userId, schoolId,
                    "An unexpected error occurred while updating user roles");
        }
    }
}
