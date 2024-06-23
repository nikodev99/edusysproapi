package com.edusyspro.api.student.controller;

import com.edusyspro.api.student.models.Guardian;
import com.edusyspro.api.student.services.GuardianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/guardian")
public class GuardianController {

    private final GuardianService guardianService;

    @Autowired
    public GuardianController(GuardianService guardianService) {
        this.guardianService = guardianService;
    }

    @GetMapping("/all")
    ResponseEntity<List<Guardian>> findAllGuardians() {
        return ResponseEntity.ok(guardianService.findAll());
    }
}
