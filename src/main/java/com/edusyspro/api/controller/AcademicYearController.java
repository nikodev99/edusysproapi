package com.edusyspro.api.controller;

import com.edusyspro.api.dto.AcademicYearDTO;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.service.impl.AcademicYearServiceImpl;
import org.springframework.http.HttpStatus;
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

    @PostMapping()
    ResponseEntity<?> insertAcademicYear(@RequestBody AcademicYearDTO academicYear) {
        try {
            return ResponseEntity.ok(academicYearService.addAcademicYear(academicYear));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{academicYearId}")
    ResponseEntity<?> updateAcademicYearFields(@PathVariable String academicYearId, @RequestBody UpdateField fields) {
        try {
            int updated = academicYearService.updateAcademicYearField(academicYearId, fields);
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

    @GetMapping("/from/{schoolId}")
    ResponseEntity<List<AcademicYearDTO>> fetchAllFromYear(
            @PathVariable String schoolId,
            @RequestParam int fromYear
    ) {
        return ResponseEntity.ok(academicYearService.getAcademicYearsFromYear(schoolId, fromYear));
    }

    @GetMapping("/from_date/{schoolId}")
    ResponseEntity<AcademicYearDTO> fetchFromDate(
            @PathVariable String schoolId,
            @RequestParam String fromDate
    ) {
        return ResponseEntity.ok(academicYearService.getAcademicYearsFromDate(
                schoolId,
                ZonedDateTime.parse(fromDate).toLocalDate()
        ));
    }

    @GetMapping("/{schoolId}")
    ResponseEntity<AcademicYearDTO> academicYear(@PathVariable String schoolId) {
        return ResponseEntity.ok(academicYearService.getCurrentAcademicYear(schoolId));
    }

    @GetMapping("/all/{schoolId}")
    ResponseEntity<List<AcademicYearDTO>> getAcademicYearList(@PathVariable String schoolId) {
        return ResponseEntity.ok(academicYearService.getAllSchoolAcademicYear(schoolId));
    }

}
