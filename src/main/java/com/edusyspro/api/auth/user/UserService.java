package com.edusyspro.api.auth.user;

import com.edusyspro.api.auth.exception.UserNotFoundException;
import com.edusyspro.api.auth.response.UserInfo;
import com.edusyspro.api.auth.response.UserInfoResponse;
import com.edusyspro.api.auth.request.SignupRequest;
import com.edusyspro.api.mail.EmailBodies;
import com.edusyspro.api.mail.EmailBodyFactory;
import com.edusyspro.api.mail.EmailRequest;
import com.edusyspro.api.mail.queue.EmailProducer;
import com.edusyspro.api.model.School;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final EmailProducer emailProducer;

    private final EmailBodyFactory factory;

    private static final int FAILED_LOGIN_ATTEMPTS = 5;

    @Autowired
    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            PasswordResetService passwordResetService,
            EmailProducer emailProducer,
            EmailBodyFactory factory
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetService = passwordResetService;
        this.emailProducer = emailProducer;
        this.factory = factory;
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

        UserSchoolRole userRoles = UserSchoolRole.builder()
                .schoolId(request.getRoles().getSchoolId())
                .school(School.builder()
                        .id(request.getRoles().getSchoolId())
                        .build())
                .roles(request.getRoles().getRoles())
                .user(user)
                .enabled(true)
                .accountNonLocked(true)
                .isActive(true)
                .failedLoginAttempts(0)
                .build();

        user.setSchoolAffiliations(List.of(userRoles));

        return userRepository.save(user);
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
    public UserInfoResponse getUserByPersonalInfo(Long personalInfoId) {
        return userRepository.findUserByPersonalInfoId(personalInfoId)
                .map(UserInfo::toResponse)
                .orElse(null);
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
        if (phoneNumber == null) return true;
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    @Transactional
    public Boolean existsByPersonalInfoId(Long personalInfoId) {
        return userRepository.existsByPersonalInfoId(personalInfoId);
    }

    @Transactional
    public Boolean existBySchoolId(String schoolId, Long personalInfoId) {
        return userRepository.countAccountInSchool(
                UUID.fromString(schoolId), personalInfoId
        ) == 1;
    }

    @Transactional
    public Long countUsers(String schoolId) {
        return userRepository.countAllUsersBySchoolId(UUID.fromString(schoolId)).orElseThrow();
    }

    /** public void updateFailedLoginAttempts(Long userId) {
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

    public void sendRegisteredUserInfo(User userInfo, String password) {
        var e = factory.create(userInfo, "", password).getRegistration();
        EmailRequest request = EmailRequest.builder()
                .from("no-reply@edusyspro.com")
                .to(List.of(userInfo.getEmail()))
                .subject("Bienvenue sur EduSysPro")
                .textBody(e.text())
                .htmlBody(e.html())
                .build();
        try {
            emailProducer.enqueue(request);
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }
    }

    public List<String> initiatePasswordReset(String email, String phoneNumber) {
        Optional<User> fetchedUser = email != null
                ? userRepository.findByEmail(email)
                : userRepository.findByPhoneNumber(phoneNumber);

        List<String> result = new ArrayList<>();

        fetchedUser.ifPresent(user -> {
            PasswordResetToken pass = passwordResetService.generatePasswordResetToken(user.getId());
            EmailBodies.EmailBody body = factory.create(user, getEmailBodyUrl(pass.getToken(), user.getEmail()))
                    .getPasswordReset();

            EmailRequest request = EmailRequest.builder()
                    .from("no-reply@edusyspro.com")
                    .to(List.of(user.getEmail()))
                    .subject("Mot de passe oublié")
                    .textBody(body.text())
                    .htmlBody(body.html())
                    .build();
            result.addAll(Arrays.asList(user.getEmail(), pass.getToken(), pass.getExpiryDate().toString()));

            try {
                emailProducer.enqueue(request);
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
                EmailBodies.EmailBody body = factory.create(user, getEmailBodyUrl(pass.getToken(), user.getEmail()))
                        .getPasswordReset();

                EmailRequest request = EmailRequest.builder()
                        .from("no-reply@edusyspro.com")
                        .to(List.of(user.getEmail()))
                        .subject("Password Reset")
                        .textBody(body.text())
                        .htmlBody(body.html())
                        .build();
                result.addAll(Arrays.asList(user.getEmail(), pass.getToken(), pass.getExpiryDate().toString()));
                try {
                    emailProducer.enqueue(request);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        return result;
    }

    //TODO a définir a qui et a quel moment envoyé ce mail de confirmation de changement de mot de passe
    public void confirmPasswordChangeEmail(String username, String email) {
        EmailBodies.EmailBody body = factory.create(User.builder().username(username).build(), "")
                .getPasswordChangeConfirmation();

        EmailRequest request = EmailRequest.builder()
                .from("no-reply@edusyspro.com")
                .to(List.of(email))
                .subject("Password Reset")
                .textBody(body.text())
                .htmlBody(body.html())
                .build();

        try {
            emailProducer.enqueue(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        return "password-reset/"+token+"/?username="+username;
    }
}
