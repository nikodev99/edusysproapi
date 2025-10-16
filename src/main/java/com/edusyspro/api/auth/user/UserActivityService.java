package com.edusyspro.api.auth.user;

import com.edusyspro.api.auth.response.UserActivityResponse;
import com.edusyspro.api.utils.Datetime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserActivityService {
    private final UserActivityRepository userActivityRepository;

    @Autowired
    public UserActivityService(UserActivityRepository userActivityRepository) {
        this.userActivityRepository = userActivityRepository;
    }

    public UserActivity saveUserActivity(UserActivity userActivity) {
        return userActivityRepository.save(userActivity);
    }

    public Page<UserActivityResponse> getAllActivities(Long accountId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return userActivityRepository.findAllByAccountId(
                accountId,
                startDate.atStartOfDay(Datetime.BRAZZA_TIME).toInstant(),
                endDate.atStartOfDay(Datetime.BRAZZA_TIME).toInstant(),
                pageable
        );
    }
}
