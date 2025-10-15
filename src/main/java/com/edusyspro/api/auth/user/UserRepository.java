package com.edusyspro.api.auth.user;

import com.edusyspro.api.auth.response.UserInfoResponse;
import com.edusyspro.api.model.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username = :username OR u.email = :username OR u.phoneNumber = :username")
    Optional<User> findByUsername(@Param("username") String username);

    @Query("""
        SELECT new com.edusyspro.api.auth.response.UserInfoResponse(u.id, s.id, u.username, u.email, i.firstName, i.lastName, s.roles, u.phoneNumber,
        s.enabled, s.accountNonLocked, s.failedLoginAttempts, s.lastLogin, u.userType, u.createdAt, u.updatedAt)
        FROM User u JOIN Individual i ON i.id = u.personalInfoId JOIN u.schoolAffiliations s WHERE u.id = ?1 AND s.schoolId = ?2
    """)
    Optional<UserInfoResponse> findUserById(@Param("id") Long id, @Param("schoolId") UUID schoolId);

    @Query("""
        SELECT new com.edusyspro.api.auth.response.UserInfoResponse(u.id, s.id, u.username, u.email, i.firstName, i.lastName, s.roles, u.phoneNumber,
        s.enabled, s.accountNonLocked, s.failedLoginAttempts, s.lastLogin, u.userType, u.createdAt, u.updatedAt)
        FROM User u JOIN Individual i ON i.id = u.personalInfoId JOIN u.schoolAffiliations s WHERE u.personalInfoId = :personalInfoId
    """)
    Optional<UserInfoResponse> findUserByPersonalInfoId(@Param("personalInfoId") Long personalInfoId);

    @Query("""
        SELECT new com.edusyspro.api.auth.response.UserInfoResponse(u.id, s.id, u.username, u.email, i.firstName, i.lastName, s.roles, u.phoneNumber,
        s.enabled, s.accountNonLocked, s.failedLoginAttempts, s.lastLogin, u.userType, u.createdAt, u.updatedAt)
        FROM User u JOIN Individual i ON i.id = u.personalInfoId JOIN u.schoolAffiliations s WHERE s.id = ?1
    """)
    Optional<UserInfoResponse> findUserById(@Param("id") Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query("""
        SELECT new com.edusyspro.api.auth.response.UserInfoResponse(u.id, s.id, u.username, u.email, i.firstName, i.lastName, s.roles, u.phoneNumber,
        s.enabled, s.accountNonLocked, s.failedLoginAttempts, s.lastLogin, u.userType, u.createdAt, u.updatedAt)
        FROM User u JOIN Individual i ON i.id = u.personalInfoId JOIN u.schoolAffiliations s WHERE s.schoolId = ?1 AND s.isActive = true
    """)
    Page<UserInfoResponse> findAllUsers(UUID schoolId, Pageable pageable);

    @Query("""
        SELECT new com.edusyspro.api.auth.response.UserInfoResponse(u.id, s.id, u.username, u.email, i.firstName, i.lastName, s.roles, u.phoneNumber,
        s.enabled, s.accountNonLocked, s.failedLoginAttempts, s.lastLogin, u.userType, u.createdAt, u.updatedAt)
        FROM User u JOIN Individual i ON i.id = u.personalInfoId JOIN u.schoolAffiliations s WHERE s.schoolId = ?1
        AND (lower(i.lastName) like lower(?2) or lower(i.firstName) like lower(?2)) AND s.isActive = true
    """)
    List<UserInfoResponse> findSearchedUsers(UUID schoolId, String searchInput);

    @Modifying
    @Transactional
    @Query("UPDATE UserSchoolRole u SET u.roles = ?2 WHERE u.id = ?1")
    int updateAccountRoles(long accountId, List<Role> roles);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByPhoneNumber(String phoneNumber);

    Boolean existsByPersonalInfoId(Long personalInfoId);

    @Query("SELECT COUNT(u.id) FROM User u JOIN u.schoolAffiliations s WHERE s.schoolId = :schoolId")
    Optional<Long> countAllUsersBySchoolId(@Param("schoolId") UUID schoolId);
}
