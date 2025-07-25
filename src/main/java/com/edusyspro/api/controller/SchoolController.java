package com.edusyspro.api.controller;

import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.service.interfaces.SchoolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/school")
public class SchoolController {
    private final SchoolService schoolService;


    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping("/{schoolId}")
    ResponseEntity<?> fetchSchoolInfo(@PathVariable String schoolId) {
        return ResponseEntity.ok(schoolService.getSchool(schoolId));
    }

    @PatchMapping("/{schoolId}")
    ResponseEntity<?> updateSchoolField(@PathVariable String schoolId, @RequestBody UpdateField fields) {
        try {
            int updated = schoolService.updateSchoolField(schoolId, fields);
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
