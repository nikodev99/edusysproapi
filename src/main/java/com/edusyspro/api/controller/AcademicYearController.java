package com.edusyspro.api.controller;

import com.edusyspro.api.data.ConstantUtils;
import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.service.impl.AcademicYearServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/academic")
public class AcademicYearController {

    private final AcademicYearServiceImpl academicYearService;

    public AcademicYearController(AcademicYearServiceImpl academicYearService) {
        this.academicYearService = academicYearService;
    }

    @GetMapping("/from")
    ResponseEntity<List<AcademicYear>> fetchAllFromYear(@RequestParam int fromYear) {
        return ResponseEntity.ok(academicYearService.getAcademicYearsFromYear(ConstantUtils.SCHOOL_ID, fromYear));
    }

    @GetMapping
    ResponseEntity<AcademicYear> academicYear() {
        return ResponseEntity.ok(academicYearService.getCurrentAcademicYear(ConstantUtils.SCHOOL_ID));
    }

    @GetMapping("/all")
    ResponseEntity<List<AcademicYear>> getAcademicYearList() {
        return ResponseEntity.ok(academicYearService.getAllSchoolAcademicYear(ConstantUtils.SCHOOL_ID));
    }

}
