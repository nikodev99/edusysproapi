package com.edusyspro.api.controller;

import com.edusyspro.api.model.Semester;
import com.edusyspro.api.service.interfaces.SemesterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/semester")
public class SemesterController {
    private final SemesterService semesterService;

    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    @PostMapping
    ResponseEntity<?> getAllSemesters(@RequestBody List<Semester> semesters) {
        try {
            return ResponseEntity.ok(semesterService.saveAll(semesters));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{schoolId}")
    ResponseEntity<?> getAllSemester(@PathVariable String schoolId) {
        return ResponseEntity.ok(semesterService.fetchAll(schoolId));
    }
}
