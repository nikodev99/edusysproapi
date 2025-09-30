package com.edusyspro.api.auth.user;

import com.edusyspro.api.auth.response.UserActivityResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {
    @Query("""
        SELECT new com.edusyspro.api.auth.response.UserActivityResponse(ua.id, ua.action, ua.actionDate, ua.ipAddress, ua.description)
        FROM UserActivity ua WHERE ua.accountId = ?1 ORDER BY ua.actionDate DESC
    """)
    List<UserActivityResponse> findAllByAccountId(long accountId);
}
