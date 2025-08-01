package com.edusyspro.api.auth.user;

import com.edusyspro.api.auth.request.UserInfoResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username = :username OR u.email = :username OR u.phoneNumber = :username")
    Optional<User> findByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.id = ?1")
    Optional<User> findUserById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query("""
        SELECT new com.edusyspro.api.auth.request.UserInfoResponse(u.id, u.username, u.email, s.roles, u.phoneNumber,
        u.enabled, u.accountNonLocked, u.failedLoginAttempts, u.lastLogin, u.userType, u.createdAt, u.updatedAt)
        FROM User u JOIN u.schoolAffiliations s WHERE s.schoolId = ?1
    """)
    List<UserInfoResponse> findAllUsers(UUID schoolId);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByPhoneNumber(String phoneNumber);

    Boolean existsByPersonalInfoId(Long personalInfoId);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.lastLogin = :lastLogin WHERE u.id = :userId")
    void updateLastLogin(@Param("userId") Long userId, @Param("lastLogin") ZonedDateTime lastLogin);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.failedLoginAttempts = :attempts WHERE u.id = :userId")
    void updateFailedLoginAttempts(@Param("userId") Long userId, @Param("attempts") Integer attempts);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.accountNonLocked = :locked WHERE u.id = :userId")
    void setAccountLocked(@Param("userId") Long userId, @Param("locked") Boolean locked);

    @Query("SELECT COUNT(u.id) FROM User u JOIN u.schoolAffiliations s WHERE s.schoolId = :schoolId")
    Optional<Long> countAllUsersBySchoolId(@Param("schoolId") UUID schoolId);
}
