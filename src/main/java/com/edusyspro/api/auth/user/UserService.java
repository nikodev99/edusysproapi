package com.edusyspro.api.auth.user;

import com.edusyspro.api.exception.sql.NotFountException;
import com.edusyspro.api.auth.request.SignupRequest;
import com.edusyspro.api.utils.Datetime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
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
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return CustomUserDetails.build(user);
    }

    public User createUser(SignupRequest request) {
        User user = request.toEntity();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //TODO Send email and phone message to user
        return userRepository.save(user);
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    public void updateLastLogin(Long userId) {
        userRepository.findUserById(userId).ifPresent(user -> {
            List<ZonedDateTime> lastLogin = user.getLastLogin();
            if (lastLogin == null) {
                lastLogin = new ArrayList<>();
            }
            lastLogin.add(Datetime.brazzavilleDatetime());
            userRepository.updateLastLogin(user.getId(), lastLogin);
        });
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
