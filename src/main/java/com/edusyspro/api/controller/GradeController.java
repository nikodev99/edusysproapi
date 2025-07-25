package com.edusyspro.api.controller;

import com.edusyspro.api.controller.utils.ControllerUtils;
import com.edusyspro.api.data.ConstantUtils;
import com.edusyspro.api.dto.GradeDTO;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.service.interfaces.GradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("grades")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PostMapping()
    ResponseEntity<?> saveGrade(@RequestBody GradeDTO grade) {
        try {
            return ResponseEntity.ok(gradeService.save(grade));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{gardeId}")
    ResponseEntity<?> updateGradeFields(@PathVariable int gardeId, @RequestBody UpdateField fields) {
        try {
            int updated = gradeService.patch(gardeId, fields);
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

    @GetMapping("/{schoolId}")
    ResponseEntity<List<GradeDTO>> getSchoolGrades(@PathVariable String schoolId) {
        return ResponseEntity.ok(gradeService.fetchAll(schoolId));
    }
}
