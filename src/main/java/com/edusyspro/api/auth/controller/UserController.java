package com.edusyspro.api.auth.controller;

import com.edusyspro.api.auth.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/{schoolId}")
    ResponseEntity<?> getAllUser(@PathVariable String schoolId) {
        return ResponseEntity.ok(userService.getAllUsers(schoolId));
    }

    @GetMapping("/count_{schoolId}")
    ResponseEntity<?> countAll(@PathVariable String schoolId) {
        return ResponseEntity.ok(userService.countUsers(schoolId));
    }
}
