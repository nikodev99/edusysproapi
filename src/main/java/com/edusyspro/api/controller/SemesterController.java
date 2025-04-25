package com.edusyspro.api.controller;

import com.edusyspro.api.data.ConstantUtils;
import com.edusyspro.api.service.interfaces.SemesterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/semester")
public class SemesterController {
    private final SemesterService semesterService;

    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    @GetMapping
    ResponseEntity<?> getAllSemester() {
        return ResponseEntity.ok(semesterService.fetchAll(ConstantUtils.SCHOOL_ID));
    }
}
