package com.edusyspro.api.auth.user;

import com.edusyspro.api.auth.request.UserInfoResponse;
import com.edusyspro.api.exception.sql.NotFountException;
import com.edusyspro.api.auth.request.SignupRequest;
import com.edusyspro.api.utils.Datetime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final PasswordResetService passwordResetService;

    /**
     * A reference to the EmailService, which is responsible for handling
     * functionalities related to email operations. This service may be used
     * for sending emails, managing email templates, handling email notifications,
     * and other related tasks integral to user management or system communication.
     */
    //private EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final int MAW_FAILED_LOGIN_ATTEMPTS = 5;

    @Autowired
    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            PasswordResetService passwordResetService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetService = passwordResetService;
    }

    @Override
    @Transactional()
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return CustomUserDetails.build(user);
    }

    @Transactional
    public User createUser(SignupRequest request) {
        User user = request.toEntity();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //TODO Send email and phone message to user
        User addedUser = userRepository.save(user);
        if (addedUser.getId() != null) {
            addedUser.setSchoolAffiliations(request.toUserSchoolRole(addedUser.getId()));
        }

        return userRepository.save(addedUser);
    }

    @Transactional
    public Page<UserInfoResponse> getAllUsers(String schoolId, Pageable pageable) {
        return userRepository.findAllUsers(UUID.fromString(schoolId), pageable);
    }

    @Transactional
    public List<UserInfoResponse> getAllSearchedUsers(String schoolId, String searchInput) {
        return userRepository.findSearchedUsers(UUID.fromString(schoolId), searchInput);
    }

    @Transactional
    public UserInfoResponse getUserById(Long userId, String schoolId) {
        return userRepository.findUserById(userId, UUID.fromString(schoolId))
                .orElseThrow();
    }

    @Transactional
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public Boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    @Transactional
    public Boolean existsByPersonalInfoId(Long personalInfoId) {
        return userRepository.existsByPersonalInfoId(personalInfoId);
    }

    @Transactional
    public void updateLastLogin(Long userId) {
        userRepository.findUserById(userId)
                .ifPresent(
                        user -> userRepository.updateLastLogin(user.getId(), Datetime.brazzavilleDatetime())
                );
    }

    @Transactional
    public Long countUsers(String schoolId) {
        return userRepository.countAllUsersBySchoolId(UUID.fromString(schoolId)).orElseThrow();
    }

    public void updateFailedLoginAttempts(Long userId) {
        userRepository.findUserById(userId).ifPresent(user -> {
            int attempts = user.getFailedLoginAttempts() + 1;
            userRepository.updateFailedLoginAttempts(user.getId(), attempts);

            if (attempts >= MAW_FAILED_LOGIN_ATTEMPTS) {
                userRepository.setAccountLocked(user.getId(), false);
                logger.warn("Account Locked for user {} due to too {} failed login attempts", user.getUsername(), attempts);

                //TODO Send email and phone message
            }
        });
    }

    public String initiatePasswordReset(String email, String phoneNumber) {
        Optional<User> fetchedUser = email != null
                ? userRepository.findByEmail(email)
                : userRepository.findByPhoneNumber(phoneNumber);

        return fetchedUser
                .map(user -> passwordResetService.generatePasswordResetToken(user.getId()))
                .orElseThrow(() -> new NotFountException("No user found with provided credentials"));

        //TODO send email and phone message to user with reset link (instead of returning the token)
    }

    public boolean resetPassword(String token, String newPassword) {
        User user = passwordResetService.validatePasswordResetToken(token);
        if (user == null) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        passwordResetService.invalidatePasswordResetToken(token);
        return true;
    }
}
