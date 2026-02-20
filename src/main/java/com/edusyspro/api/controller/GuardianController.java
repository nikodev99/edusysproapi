package com.edusyspro.api.controller;

import com.edusyspro.api.dto.GuardianDTO;
import com.edusyspro.api.service.interfaces.GuardianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guardian")
public class GuardianController {

    private final GuardianService guardianService;

    @Autowired
    public GuardianController(GuardianService guardianService) {
        this.guardianService = guardianService;
    }

    @GetMapping("/search-all")
    ResponseEntity<List<GuardianDTO>> findAllGuardians(@RequestParam String searchInput) {
        return ResponseEntity.ok(guardianService.searchAll(searchInput));
    }

    @GetMapping("/{guardianId}")
    ResponseEntity<GuardianDTO> findGuardianById(@PathVariable String guardianId) {
        return ResponseEntity.ok(guardianService.findGuardianById(guardianId));
    }

    @GetMapping("/withStudent/{schoolId}/{guardianId}")
    ResponseEntity<GuardianDTO> findGuardianByIdWithStudents(
            @PathVariable String schoolId,
            @PathVariable String guardianId
    ) {
        return ResponseEntity.ok(guardianService.findGuardianByIdWithStudents(schoolId, guardianId));
    }
}
