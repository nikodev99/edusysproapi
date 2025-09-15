package com.edusyspro.api.auth.user;

import com.edusyspro.api.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserSchoolRoleRepository extends JpaRepository<UserSchoolRole, Long> {
    @Query("SELECT usr FROM UserSchoolRole usr WHERE usr.userId = :userId AND usr.isActive = true")
    List<UserSchoolRole> findByUserIdAndIsActiveTrue(@Param("userId") Long userId);

    @Query("SELECT usr FROM UserSchoolRole usr WHERE usr.userId = :userId AND usr.schoolId = :schoolId AND usr.isActive = true")
    UserSchoolRole findByUserIdAndSchoolIdAndIsActiveTrue(@Param("userId") Long userId, @Param("schoolId") UUID schoolId);

    @Query("SELECT usr FROM UserSchoolRole usr JOIN usr.roles r WHERE usr.userId = :userId AND r = :role AND usr.isActive = true")
    List<UserSchoolRole> findByUserIdAndRole(@Param("userId") Long userId, @Param("role") Role role);

    @Modifying
    @Transactional
    @Query("UPDATE UserSchoolRole usr SET usr.roles = :roles WHERE usr.userId = :userId AND usr.schoolId = :schoolId")
    int updateUserRole(@Param("userId") Long userId, @Param("schoolId") UUID schoolId, @Param("role") List<Role> roles);
}
