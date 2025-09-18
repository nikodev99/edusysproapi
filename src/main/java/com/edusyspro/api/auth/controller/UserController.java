package com.edusyspro.api.auth.controller;

import com.edusyspro.api.auth.token.refresh.RefreshTokenService;
import com.edusyspro.api.auth.user.UserSchoolRoleService;
import com.edusyspro.api.auth.user.UserService;
import com.edusyspro.api.controller.utils.ControllerUtils;
import com.edusyspro.api.dto.custom.UpdateField;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final UserSchoolRoleService userAccount;

    public UserController(
            UserService userService, RefreshTokenService refreshTokenService,
            UserSchoolRoleService userSchoolRoleService
    ) {
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.userAccount = userSchoolRoleService;
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

    @GetMapping("/logins/{userId}")
    ResponseEntity<?> getUserLogins(@PathVariable Long userId) {
        return ResponseEntity.ok(refreshTokenService.findUserActiveLogins(userId));
    }

    @GetMapping("/count_{schoolId}")
    ResponseEntity<?> countAll(@PathVariable String schoolId) {
        return ResponseEntity.ok(userService.countUsers(schoolId));
    }

    @PatchMapping("/enable/{accountId}")
    ResponseEntity<?> enableAccount (@PathVariable Long accountId, @RequestBody UpdateField field) {
        return ResponseEntity.ok(userAccount.updateAccount(accountId, field));
    }

    @PatchMapping("/lock/{accountId}")
    ResponseEntity<?> lockAccount (@PathVariable Long accountId, @RequestBody UpdateField field) {
        return ResponseEntity.ok(userAccount.updateAccount(accountId, field));
    }
}
