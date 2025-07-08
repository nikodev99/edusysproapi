package com.edusyspro.api.controller;

import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.service.impl.AcademicYearServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/academic")
public class AcademicYearController {

    private final AcademicYearServiceImpl academicYearService;

    public AcademicYearController(AcademicYearServiceImpl academicYearService) {
        this.academicYearService = academicYearService;
    }

    @GetMapping("/from/{schoolId}")
    ResponseEntity<List<AcademicYear>> fetchAllFromYear(
            @PathVariable String schoolId,
            @RequestParam int fromYear
    ) {
        return ResponseEntity.ok(academicYearService.getAcademicYearsFromYear(schoolId, fromYear));
    }

    @GetMapping("/from_date/{schoolId}")
    ResponseEntity<AcademicYear> fetchFromDate(
            @PathVariable String schoolId,
            @RequestParam String fromDate
    ) {
        return ResponseEntity.ok(academicYearService.getAcademicYearsFromDate(
                schoolId,
                ZonedDateTime.parse(fromDate).toLocalDate()
        ));
    }

    @GetMapping("/{schoolId}")
    ResponseEntity<AcademicYear> academicYear(@PathVariable String schoolId) {
        return ResponseEntity.ok(academicYearService.getCurrentAcademicYear(schoolId));
    }

    @GetMapping("/all/{schoolId}")
    ResponseEntity<List<AcademicYear>> getAcademicYearList(@PathVariable String schoolId) {
        return ResponseEntity.ok(academicYearService.getAllSchoolAcademicYear(schoolId));
    }

}
