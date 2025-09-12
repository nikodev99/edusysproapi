package com.edusyspro.api.auth.controller;

import com.edusyspro.api.auth.user.UserService;
import com.edusyspro.api.controller.utils.ControllerUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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

    @GetMapping("/count_{schoolId}")
    ResponseEntity<?> countAll(@PathVariable String schoolId) {
        return ResponseEntity.ok(userService.countUsers(schoolId));
    }
}
