package com.edusyspro.api.auth.controller;

import com.edusyspro.api.auth.request.ChangePassword;
import com.edusyspro.api.auth.request.UserActivityRequest;
import com.edusyspro.api.auth.response.MessageResponse;
import com.edusyspro.api.auth.token.refresh.RefreshTokenService;
import com.edusyspro.api.auth.token.refresh.UserLoginResponse;
import com.edusyspro.api.auth.user.*;
import com.edusyspro.api.controller.utils.ControllerUtils;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.helper.log.L;
import com.edusyspro.api.service.interfaces.IndividualService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final UserSchoolRoleService userAccount;
    private final UserActivityService userActivityService;
    private final IndividualService individualService;

    public UserController(
            UserService userService,
            RefreshTokenService refreshTokenService,
            UserSchoolRoleService userSchoolRoleService,
            UserActivityService userActivityService,
            IndividualService individualService
    ) {
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.userAccount = userSchoolRoleService;
        this.userActivityService = userActivityService;
        this.individualService = individualService;
    }

    @RequestMapping("/{schoolId}")
    ResponseEntity<?> getAllUser(
            @PathVariable String schoolId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sortCriteria
    ) {
        Pageable pageable = ControllerUtils.setSort(page, size, sortCriteria);
        return ResponseEntity.ok(userService.getAllUsers(schoolId, pageable));
    }

    @RequestMapping("/{schoolId}/")
    ResponseEntity<?> getAllUser(
            @PathVariable String schoolId,
            @RequestParam String query
    ) {
        return ResponseEntity.ok(userService.getAllSearchedUsers(schoolId, query));
    }

    @RequestMapping("/{userId}/{schoolId}")
    ResponseEntity<?> getUser(@PathVariable Long userId, @PathVariable String schoolId) {
        return ResponseEntity.ok(userService.getUserById(userId, schoolId));
    }

    @GetMapping("/login/{accountId}")
    ResponseEntity<?> getUserLogin(@PathVariable Long accountId) {
        return ResponseEntity.ok(refreshTokenService.findUserActiveLogin(accountId));
    }

    @GetMapping("/all_logins/{accountId}")
    ResponseEntity<List<UserLoginResponse>> getAllUserLogins(@PathVariable Long accountId) {
        return ResponseEntity.ok(refreshTokenService.findAccountActiveLogins(accountId));
    }

    @PostMapping("/logout/{sessionId}")
    ResponseEntity<?> logoutActiveLogin(@PathVariable Long sessionId) {
        try {
            refreshTokenService.logoutActiveLogin(sessionId);
            return ResponseEntity.ok((MessageResponse.builder()
                    .message("Déconnexion à la session exécuté avec succès.")
                    .timestamp(Instant.now().toString())
                    .build()
            ));

        }catch (Exception ex){
            return ResponseEntity.badRequest().body(MessageResponse.builder()
                    .message("Impossible de logout de cette session: " + ex.getMessage())
                    .timestamp(Instant.now().toString())
                    .isError(true)
                    .build()
            );
        }
    }

    @GetMapping("/count_{schoolId}")
    ResponseEntity<?> countAll(@PathVariable String schoolId) {
        return ResponseEntity.ok(userService.countUsers(schoolId));
    }

    @PostMapping("/activity")
    ResponseEntity<?> SaveUserActivity(@RequestBody @Valid UserActivityRequest activityData, HttpServletRequest request) {
        UserActivity userActivity = UserActivity.builder()
                .action(activityData.getAction())
                .description(activityData.getDescription())
                .ipAddress(ControllerUtils.getClientIpAddress(request))
                .accountId(activityData.getAccountId())
                .build();

        return ResponseEntity.ok(userActivityService.saveUserActivity(userActivity));
    }

    @GetMapping("/activity/{accountId}")
    ResponseEntity<?> getUserActivities(
            @PathVariable Long accountId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sortCriteria,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        LocalDate start = startDate == null ? LocalDate.now().minusMonths(1) : ControllerUtils.parseDate(startDate);
        LocalDate end = endDate == null ? LocalDate.now() : ControllerUtils.parseDate(endDate);

        Pageable pageable = ControllerUtils.setSort(page, size, sortCriteria);

        return ResponseEntity.ok(userActivityService.getAllActivities(accountId, start, end, pageable));
    }

    @PatchMapping("/enable/{accountId}")
    ResponseEntity<?> enableAccount (@PathVariable Long accountId, @RequestBody UpdateField field) {
        return ResponseEntity.ok(userAccount.updateAccount(accountId, field));
    }

    @PatchMapping("/lock/{accountId}")
    ResponseEntity<?> lockAccount (@PathVariable Long accountId, @RequestBody UpdateField field) {
        return ResponseEntity.ok(userAccount.updateAccount(accountId, field));
    }

    @PatchMapping("/role/{accountId}")
    ResponseEntity<?> accountRoles (@PathVariable Long accountId, @RequestBody UpdateField field) {
        var roles = ControllerUtils.normalizeRoles(field);
        var data = new UpdateField(field.field(), roles);
        return ResponseEntity.ok(userAccount.updateAccount(accountId, data));
    }

    @PatchMapping("/remove/{accountId}")
    ResponseEntity<?> removeAccount (@PathVariable Long accountId, @RequestBody UpdateField field) {
        return ResponseEntity.ok(userAccount.updateAccount(accountId, field));
    }

    @GetMapping("/ind")
    ResponseEntity<?> findUserPersonalInfo(@RequestParam String search) {
        return ResponseEntity.ok(individualService.getSearchedUserPersonalInfo(search));
    }

    @GetMapping("/personal/{personalInfoId}")
    ResponseEntity<?> findUserByPersonalInfo(@PathVariable Long personalInfoId) {
        return ResponseEntity.ok(userService.getUserByPersonalInfo(personalInfoId));
    }

    @GetMapping("/address/{personalId}")
    ResponseEntity<?> getUserAddress(@PathVariable Long personalId) {
        return ResponseEntity.ok(individualService.getAddress(personalId));
    }

    @PostMapping("/change-password")
    ResponseEntity<?> changePassword(@Valid @RequestBody ChangePassword request) {
        try {
            userService.changePassword(request.userId(), request.oldPassword(), request.newPassword());
            return ResponseEntity.ok(MessageResponse.builder()
                    .message("Mot de passe changé avec succès. Veuillez vous reconnecter pour la sécurité")
                    .timestamp(Instant.now().toString())
                    .build()
            );
        }catch (Exception e) {
            L.error("Error processing password reset request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponse.builder()
                            .message("Error processing password reset request. Error: " + e.getMessage())
                            .timestamp(Instant.now().toString())
                            .isError(true)
                            .build()
                    );
        }
    }
}
