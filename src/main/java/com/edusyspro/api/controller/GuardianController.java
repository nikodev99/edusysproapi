package com.edusyspro.api.controller;

import com.edusyspro.api.dto.Guardian;
import com.edusyspro.api.service.interfaces.GuardianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{guardianId}")
    ResponseEntity<Guardian> findGuardianById(@PathVariable String guardianId) {
        return ResponseEntity.ok(guardianService.findGuardianById(guardianId));
    }

    @GetMapping("/withStudent/{guardianId}")
    ResponseEntity<Guardian> findGuardianByIdWithStudents(@PathVariable String guardianId) {
        return ResponseEntity.ok(guardianService.findGuardianByIdWithStudents(guardianId));
    }
}
