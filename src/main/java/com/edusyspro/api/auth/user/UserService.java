package com.edusyspro.api.auth.user;

import com.edusyspro.api.auth.exception.UserNotFoundException;
import com.edusyspro.api.auth.response.UserInfoResponse;
import com.edusyspro.api.auth.request.SignupRequest;
import com.edusyspro.api.mail.EmailRequest;
import com.edusyspro.api.mail.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
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
    private final EmailService emailService;

    private static final int FAILED_LOGIN_ATTEMPTS = 5;

    @Value("${ui.email-base-url}")
    private String UI_BASE_URL;

    @Autowired
    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            PasswordResetService passwordResetService,
            EmailService emailService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetService = passwordResetService;
        this.emailService = emailService;
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
    public Long countUsers(String schoolId) {
        return userRepository.countAllUsersBySchoolId(UUID.fromString(schoolId)).orElseThrow();
    }

    /*public void updateFailedLoginAttempts(Long userId) {
        userRepository.findUserById(userId).ifPresent(user -> {
            int attempts = user.getFailedLoginAttempts() + 1;
            userRepository.updateFailedLoginAttempts(user.getId(), attempts);

            if (attempTts >= MAW_FAILED_LOGIN_ATTEMPS) {
                userRepository.setAccountLocked(user.getId(), false);
                logger.warn("Account Locked for user {} due to too {} failed login attempts", user.getUsername(), attempts);

                //TODO Send email and phone message
            }
        });
    }*/

    public List<String> initiatePasswordReset(String email, String phoneNumber) {
        Optional<User> fetchedUser = email != null
                ? userRepository.findByEmail(email)
                : userRepository.findByPhoneNumber(phoneNumber);

        List<String> result = new ArrayList<>();

        fetchedUser.ifPresent(user -> {
            PasswordResetToken pass = passwordResetService.generatePasswordResetToken(user.getId());
            EmailRequest request = EmailRequest.builder()
                    .from("no-reply@edusyspro.com")
                    .to(List.of("gu.edusyspro@gmail.com" /*user.getEmail()*/))
                    .subject("Password Reset")
                    .htmlBody("<div>Cliquez sur le lien ci-dessous pour réinitialiser votre mot de passe.<br>"+
                            getEmailBodyUrl(pass.getToken(), user.getEmail())+
                            "<br>Le lien sera expiré dans une heure.</div>")
                    .build();
            result.addAll(Arrays.asList(user.getEmail(), pass.getToken(), pass.getExpiryDate().toString()));

            try {
                emailService.send(request);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        //TODO phone message to user with reset link (instead of returning the token)
        return result;
    }

    public List<String> initiatePasswordReset(Long userId) {
        List<String> result = new ArrayList<>();
        userRepository.findById(userId)
            .ifPresent(user -> {
                PasswordResetToken pass = passwordResetService.generatePasswordResetToken(user.getId());
                EmailRequest request = EmailRequest.builder()
                        .from("no-reply@edusyspro.com")
                        .to(List.of("gu.edusyspro@gmail.com" /*user.getEmail()*/))
                        .subject("Password Reset")
                        .htmlBody("<div>Cliquez sur le lien ci-dessous pour réinitialiser votre mot de passe.<br>"+
                                getEmailBodyUrl(pass.getToken(), user.getId().toString())+
                                "<br>Le lien sera expiré dans une heure.</div>")
                        .build();
                result.addAll(Arrays.asList(user.getEmail(), pass.getToken(), pass.getExpiryDate().toString()));
                try {
                    emailService.send(request);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        return result;
    }

    public UserInfoResponse validatePasswordResetToken(String token) {
       User user = passwordResetService.validatePasswordResetToken(token);
       return UserInfoResponse.fromUser(user);
    }

    public boolean resetPassword(String token, String newPassword) {
        User user = passwordResetService.validatePasswordResetToken(token);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        passwordResetService.invalidatePasswordResetToken(token);
        return true;
    }

    public void changePassword(Long userId, String oldPassword, String newPassword) {
        if (userId == null || oldPassword == null || newPassword == null) {
            throw new IllegalArgumentException("Missing parameter(s)");
        }
        if (oldPassword.equals(newPassword)) {
            throw new IllegalArgumentException("Le nouveau mot de passe doit être différent de l'ancien");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BadCredentialsException("L'ancien mot de passe ne correspond pas");
        }

        //TODO optional: password strength and history checks here

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

    }

    private String getEmailBodyUrl(String token, String username) {
        final String URL_LINK = UI_BASE_URL+"/password-reset/"+token+"/?username="+username;
        return "<a href=\""+URL_LINK+"\">"+URL_LINK+"</a>";
    }
}
