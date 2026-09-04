package com.edusyspro.api.controller;

import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.model.SemesterTemplate;
import com.edusyspro.api.service.impl.SemesterTemplateService;
import com.edusyspro.api.service.interfaces.SemesterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/semester")
public class SemesterController {
    private final SemesterService semesterService;
    private final SemesterTemplateService semesterTemplateService;

    public SemesterController(
            SemesterService semesterService,
            SemesterTemplateService semesterTemplateService
    ) {
        this.semesterService = semesterService;
        this.semesterTemplateService = semesterTemplateService;
    }

    @PostMapping
    ResponseEntity<?> saveSemester(@RequestBody SemesterTemplate semester) {
        try {
            return ResponseEntity.ok(semesterTemplateService.saveOrUpdate(semester));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/all")
    ResponseEntity<?> saveAllSemesters(@RequestBody List<SemesterTemplate> semesters) {
        try {
            return ResponseEntity.ok(semesterTemplateService.saveAll(semesters));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = {"/{schoolId}", "/all/{schoolId}"})
    ResponseEntity<?> getAllSemester(@PathVariable String schoolId) {
        return ResponseEntity.ok(semesterService.fetchAll(schoolId));
    }

    @GetMapping()
    ResponseEntity<?> getAllSemesterByAcademicYear(@RequestParam String academicYear) {
        return ResponseEntity.ok(semesterService.fetchAllByOtherEntityId(academicYear));
    }

    @PatchMapping("/{semesterId}")
    ResponseEntity<?> updateAcademicYearFields(@PathVariable int semesterId, @RequestBody UpdateField fields) {
        try {
            int updated = semesterService.patch(semesterId, fields);
            if (updated > 0) {
                return ResponseEntity.ok("Modification " + fields.field() + " effective");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("School not found or update failed");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
