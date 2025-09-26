package com.edusyspro.api.auth.user;

import com.edusyspro.api.auth.response.UserActivityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<UserActivityResponse> getAllActivities(Long accountId) {
        return userActivityRepository.findAllByAccountId(accountId);
    }
}
