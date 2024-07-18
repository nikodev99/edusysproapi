package com.edusyspro.api.school.controllers;

import com.edusyspro.api.data.ConstantUtils;
import com.edusyspro.api.school.entities.AcademicYear;
import com.edusyspro.api.school.services.AcademicYearService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/academic")
public class AcademicYearController {

    private final AcademicYearService academicYearService;

    public AcademicYearController(AcademicYearService academicYearService) {
        this.academicYearService = academicYearService;
    }

    @GetMapping
    ResponseEntity<AcademicYear> academicYear() {
        return ResponseEntity.ok(academicYearService.getAcademicYear(ConstantUtils.SCHOOL_ID));
    }

}
