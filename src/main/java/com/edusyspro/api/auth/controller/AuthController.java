package com.edusyspro.api.auth.controller;

import com.edusyspro.api.auth.request.*;
import com.edusyspro.api.auth.response.LoginResponse;
import com.edusyspro.api.auth.response.MessageResponse;
import com.edusyspro.api.auth.token.refresh.RefreshEntity;
import com.edusyspro.api.auth.token.refresh.RefreshTokenRequest;
import com.edusyspro.api.auth.token.jwt.JWTUtils;
import com.edusyspro.api.auth.token.jwt.JwtAuthentificationEntryPoint;
import com.edusyspro.api.auth.token.refresh.RefreshTokenService;
import com.edusyspro.api.auth.user.CustomUserDetails;
import com.edusyspro.api.auth.user.UserSchoolRole;
import com.edusyspro.api.auth.user.UserSchoolRoleService;
import com.edusyspro.api.auth.user.UserService;
import com.edusyspro.api.dto.IndividualUser;
import com.edusyspro.api.service.interfaces.IndividualService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final JwtAuthentificationEntryPoint authenticationEntryPoint;

    private final UserService userService;

    private final RefreshTokenService refreshTokenService;

    private final IndividualService individualService;

    private final UserSchoolRoleService userSchoolRoleService;

    private final JWTUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            JwtAuthentificationEntryPoint authenticationEntryPoint,
            UserService userService,
            RefreshTokenService refreshTokenService,
            IndividualService individualService,
            UserSchoolRoleService userSchoolRoleService,
            JWTUtils jwtUtils
    ) {
        this.authenticationManager = authenticationManager;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.individualService = individualService;
        this.userSchoolRoleService = userSchoolRoleService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();

            List<UserSchoolRole> schoolAffiliations = userSchoolRoleService.getActiveSchoolsForUser(userPrincipal.getId());

            if (schoolAffiliations.isEmpty()) {
                logger.warn("User {} has no school affiliations", loginRequest.getUsername());
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(MessageResponse.builder().message("User has no school affiliations").build());
            }

            if (schoolAffiliations.size() == 1) {
                return handleSingleSchoolLogin(userPrincipal, schoolAffiliations.get(0), request);
            }

            return handleMultiSchoolLogin(userPrincipal, schoolAffiliations);

        }catch (BadCredentialsException e) {
            logger.warn("Failed login attempt for user: {} from IP: {}",
                    loginRequest.getUsername(), authenticationEntryPoint.getClientIpAddress(request));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(MessageResponse.builder()
                            .message("Invalid username or password")
                            .timestamp(Instant.now().toString())
                            .build());

        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(MessageResponse.builder()
                            .message("Account is disabled")
                            .timestamp(Instant.now().toString())
                            .build());

        } catch (LockedException e) {
            return ResponseEntity.status(HttpStatus.LOCKED)
                    .body(MessageResponse.builder()
                            .message("Account is locked")
                            .timestamp(Instant.now().toString())
                            .build());

        } catch (Exception e) {
            logger.error("Authentication error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponse.builder()
                            .message("Authentication failed")
                            .timestamp(Instant.now().toString())
                            .build());
        }
    }

    @PostMapping("/select_school/")
    ResponseEntity<?> selectSchool(@Valid @RequestBody SchoolSelectionRequest selection, HttpServletRequest request) {
        String token = jwtUtils.extractTokenFromHeader(request.getHeader("Authorization"));
        if (token == null || jwtUtils.isTokenExpired(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(MessageResponse.builder()
                            .message("Invalid or expired token")
                            .timestamp(Instant.now().toString())
                            .build());
        }

        if (!jwtUtils.requiresSchoolSelection(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(MessageResponse.builder()
                            .message("Token does not allow school selection")
                            .timestamp(Instant.now().toString())
                            .build());
        }
        Long userId = jwtUtils.getUserIdFromToken(token);
        String username = jwtUtils.getUsernameFromToken(token);

        try {
            CustomUserDetails userPrincipal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserSchoolRole userSchoolRole = userSchoolRoleService.getUserSchoolRole(userId, selection.getSchoolId());
            return handleSingleSchoolLogin(userPrincipal, userSchoolRole, request);
        }catch (BadCredentialsException e) {
            logger.warn("Failed login for user: {} from IP: {}",
                    username, authenticationEntryPoint.getClientIpAddress(request));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(MessageResponse.builder()
                            .message("Invalid username or password")
                            .timestamp(Instant.now().toString())
                            .build());

        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(MessageResponse.builder()
                            .message("Account is disabled")
                            .timestamp(Instant.now().toString())
                            .build());

        } catch (LockedException e) {
            return ResponseEntity.status(HttpStatus.LOCKED)
                    .body(MessageResponse.builder()
                            .message("Account is locked")
                            .timestamp(Instant.now().toString())
                            .build());

        } catch (Exception e) {
            logger.error("Authentication error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponse.builder()
                            .message("Authentication failed")
                            .timestamp(Instant.now().toString())
                            .build());
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            if (userService.existsByUsername(signUpRequest.getUsername())) {
                return ResponseEntity.badRequest()
                        .body(MessageResponse.builder().message("Username is already taken").build());
            }

            if (userService.existsByEmail(signUpRequest.getEmail())) {
                return ResponseEntity.badRequest()
                        .body(MessageResponse.builder().message("Email is already in use").build());
            }

            userService.createUser(signUpRequest);

            logger.info("New user registered: {}", signUpRequest.getUsername());

            return ResponseEntity.ok(MessageResponse.builder().message("User registered successfully").build());

        } catch (Exception e) {
            logger.error("Registration error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponse.builder().message("Registration failed").build());
        }
    }

    @PostMapping("/register-new-school")
    public ResponseEntity<?> registerNewSchool(@Valid @RequestBody AssignToUserRequest signupRequest) {
        try {
            boolean isUserExist = userSchoolRoleService.isUserSchoolRoleExist(
                    signupRequest.userId(),
                    signupRequest.schoolId()
            );

            if(isUserExist) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                        MessageResponse.builder()
                                .message("L'utilisateur à déjà une affiliation avec cette école")
                                .isError(true)
                                .timestamp(Instant.now().toString())
                                .build()
                );
            }
            userSchoolRoleService.assignUserToSchool(signupRequest);

            logger.info("New school affiliation registered for user: {}", signupRequest.userId());

            return ResponseEntity.ok(MessageResponse.builder()
                    .message("Affiliation à une nouvelle école enregistré avec succès.")
                            .timestamp(Instant.now().toString())
                    .build()
            );
        }catch (Exception e) {
            logger.error("Registration error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponse.builder().message("Registration failed").build());
        }
    }

    @PostMapping("/refresh")
    ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        try {
            String refreshToken = request.getRefreshToken();

            if (!jwtUtils.isRefreshToken(refreshToken) || jwtUtils.isTokenExpired(refreshToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(MessageResponse.builder().message("Invalid or expired refresh token").build());
            }

            String username = jwtUtils.getUsernameFromToken(refreshToken);
            CustomUserDetails userDetails = (CustomUserDetails) userService.loadUserByUsername(username);

            String accessToken = jwtUtils.generateToken(userDetails);
            RefreshEntity token = refreshTokenService.findAndValidateRefreshToken(refreshToken);

            IndividualUser user = individualService.getLoginUser(
                    userDetails.getPersonalInfoId(),
                    userDetails.getUserType()
            );
            LoginResponse response = LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .userAgent(token.getUserAgent())
                    .email(userDetails.getEmail())
                    .username(userDetails.getUsername())
                    .tokenType("Bearer")
                    .id(userDetails.getPersonalInfoId())
                    .roles(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                    .accountId(userDetails.getSchoolAffiliations().get(0).getId())
                    .user(user)
                    .userType(userDetails.getUserType())
                    .build();

            return ResponseEntity.ok(response);

        }catch (Exception e) {
            logger.error("Token refresh error", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(MessageResponse.builder().message("Token refresh failed").build());
        }
    }

    @PostMapping("/logout")
    ResponseEntity<?> logout(@Valid @RequestBody LogoutRequest logoutRequest, Authentication authentication) {
        try {
            if (authentication != null && authentication.isAuthenticated()) {
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

                if (logoutRequest.getRefreshToken() != null) {
                    refreshTokenService.revokeRefreshToken(logoutRequest.getRefreshToken());
                }

                logger.info("User {} logged out", userDetails.getUsername());
            }

            return ResponseEntity.ok(MessageResponse.builder().message("Logout successful").build());
        }catch (Exception e) {
            logger.error("Logout error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponse.builder().message("Logout failed").build());
        }
    }

    @PostMapping("/password-forgot")
    ResponseEntity<?> forgetPassword(@Valid @RequestBody ForgotPasswordRequest request, HttpServletRequest httpRequest) {
        try {
            List<String> res = userService.initiatePasswordReset(request.getEmail(), request.getPhoneNumber());
            var ip = authenticationEntryPoint.getClientIpAddress(httpRequest);
            return ResponseEntity.ok(MessageResponse.builder()
                    .message("Mail pour réinitialiser votre mot de passe à été envoyé avec succès. vérifier votre compte email")
                    .description(
                            String.format(
                                    "Réinitialisation du mot de passe pour l'email=[%s] par IP=[%s]. Token généré: [%s], expiré le: [%s]. Status: [ENVOYÉ].",
                                    res.get(0), ip, res.get(1), res.get(2)
                            ))
                    .timestamp(Instant.now().toString())
                    .build()
            );
        }catch (Exception e) {
            logger.error("Error processing password reset request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponse.builder()
                            .message("Mail could not be send to " + request.getEmail())
                            .timestamp(Instant.now().toString())
                            .build()
                    );
        }
    }

    @PostMapping("/password-reset/{userId}")
    ResponseEntity<?> resetPassword(@PathVariable Long userId, HttpServletRequest request) {
        try {
            List<String> res = userService.initiatePasswordReset(userId);
            var ip = authenticationEntryPoint.getClientIpAddress(request);
            return ResponseEntity.ok(MessageResponse.builder()
                    .message("Mail pour réinitialiser le mot de passe de l'utilisateur à été envoyé avec succès. Notification pour vérifier le mail générer")
                    .description(
                            String.format(
                                    "Réinitialisation du mot de passe pour l'email=[%s] par IP=[%s]. Token généré: [%s], expiré le: [%s]. Status: [ENVOYÉ].",
                                    res.get(0), ip, res.get(1), res.get(2)
                    ))
                    .timestamp(Instant.now().toString())
                    .build()
            );
        }catch (Exception e) {
            logger.error("Error processing password reset request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponse.builder()
                            .message("Mail could not be send to " + e.getMessage())
                            .timestamp(Instant.now().toString())
                            .build()
                    );
        }
    }

    @GetMapping("/validate-token")
    ResponseEntity<?> validateToken(@RequestParam String token) {
        try {
            return ResponseEntity.ok(userService.validatePasswordResetToken(token));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    MessageResponse.builder()
                            .message("Validation failed: " + e.getMessage())
                            .timestamp(Instant.now().toString())
                            .isError(true)
                            .build()
            );
        }
    }

    @PostMapping("/reset")
    ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        try {
            boolean success = userService.resetPassword(request.getToken(), request.getNewPassword());
            if (success) {
                return ResponseEntity.ok(MessageResponse.builder()
                        .message("Mot de passe modifié avec succès")
                        .timestamp(Instant.now().toString())
                        .build()
                );
            }else {
                return ResponseEntity.badRequest()
                        .body(MessageResponse.builder()
                            .message("Token invalide ou expiré. Merci de procéder à nouveau à réinitialiser votre mot de passe.")
                            .timestamp(Instant.now().toString())
                            .isError(true)
                            .build()
                );
            }
        }catch (Exception e) {
            logger.error("Error processing password reset request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponse.builder()
                            .message("Error processing password reset request. Error: " + e.getMessage())
                            .timestamp(Instant.now().toString())
                            .isError(true)
                            .build()
                    );
        }
    }

    @GetMapping("/user/{personalInfoId}")
    ResponseEntity<Boolean> findUserExists(@PathVariable Long personalInfoId) {
        return ResponseEntity.ok(userService.existsByPersonalInfoId(personalInfoId));
    }

    private ResponseEntity<?> handleSingleSchoolLogin(CustomUserDetails userPrincipal, UserSchoolRole schoolAffiliation, HttpServletRequest request) {
        try {
            String accessToken = jwtUtils.generateTokenWithContext(userPrincipal, schoolAffiliation);

            String userAgent = request.getHeader("User-Agent");
            String clientType = request.getHeader("sec-ch-ua");
            String clientIp = authenticationEntryPoint.getClientIpAddress(request);

            RefreshEntity refreshEntity = refreshTokenService.findByUserAndClientType(userPrincipal.getId(), clientType);
            String refreshToken;

            if (refreshEntity == null) {
                refreshToken = jwtUtils.generateRefreshToken(userPrincipal);
                String deviceType = request.getHeader("sec-ch-ua-platform");
                Date expiryDate = jwtUtils.getExpirationDateFromToken(refreshToken);
                RefreshEntity toSave = RefreshEntity.builder()
                        .userId(userPrincipal.getId())
                        .refreshToken(refreshToken)
                        .userAgent(userAgent)
                        .clientType(clientType)
                        .accountId(schoolAffiliation.getId())
                        .clientIp(clientIp)
                        .expiryDate(expiryDate.toInstant())
                        .createdAt(Instant.now())
                        .lastUsedAt(Instant.now())
                        .isActive(true)
                        .refreshCount(0)
                        .deviceFingerprint(deviceType)
                        .build();
                refreshTokenService.saveRefreshToken(toSave);
            }else {
                refreshToken = refreshEntity.getRefreshToken();
            }

            userSchoolRoleService.updateLastLogin(userPrincipal.getId(), schoolAffiliation.getSchoolId());
            IndividualUser user = individualService.getLoginUser(
                    userPrincipal.getPersonalInfoId(),
                    userPrincipal.getUserType()
            );

            LoginResponse response = LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .userAgent(userAgent)
                    .email(userPrincipal.getEmail())
                    .username(userPrincipal.getUsername())
                    .tokenType("Bearer")
                    .enabled(schoolAffiliation.getEnabled())
                    .accountNonLocked(schoolAffiliation.getAccountNonLocked())
                    .id(userPrincipal.getPersonalInfoId())
                    .roles(userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                    .accountId(schoolAffiliation.getId())
                    .user(user)
                    .userType(userPrincipal.getUserType())
                    .build();

            logger.info(
                    "User {} successfully authenticated from IP: {}",
                    userPrincipal.getUsername(), clientIp
            );

            return ResponseEntity.ok(response);
        }catch (Exception e) {
            logger.error("Single school login error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponse.builder()
                            .message("Single school login failed")
                            .timestamp(Instant.now().toString())
                            .build()
                    );
        }
    }

    private ResponseEntity<?> handleMultiSchoolLogin(CustomUserDetails userPrincipal, List<UserSchoolRole> schoolAffiliations) {
        try {
            if (schoolAffiliations.size() > 1) {
                String accessToken = jwtUtils.generateToken(userPrincipal);

                IndividualUser user = individualService.getLoginUser(
                        userPrincipal.getPersonalInfoId(),
                        userPrincipal.getUserType()
                );
                LoginResponse response = LoginResponse.builder()
                        .accessToken(accessToken)
                        .email(userPrincipal.getEmail())
                        .username(userPrincipal.getUsername())
                        .tokenType("Bearer")
                        .id(userPrincipal.getPersonalInfoId())
                        .user(user)
                        .userType(userPrincipal.getUserType())
                        .build();
                return ResponseEntity.ok(response);
            }
            logger.error("User {} do not have more than one school affiliation", userPrincipal.getUsername());
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(MessageResponse.builder()
                            .message("Multi school login failed")
                            .timestamp(Instant.now().toString())
                            .build()
                    );
        }catch (Exception e) {
            logger.error("Multi school login error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponse.builder()
                            .message("Multi school login failed")
                            .timestamp(Instant.now().toString())
                            .build()
                    );
        }
    }
}
