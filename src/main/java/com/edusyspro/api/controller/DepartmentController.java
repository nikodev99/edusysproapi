package com.edusyspro.api.controller;

import com.edusyspro.api.dto.DepartmentDTO;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.service.interfaces.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping()
    ResponseEntity<?> saveDepartment(@RequestBody DepartmentDTO departmentDTO) {
        try {
            return ResponseEntity.ok(departmentService.save(departmentDTO));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{departmentId}")
    ResponseEntity<?> updateDepartmentFields(@PathVariable int departmentId, @RequestBody UpdateField fields) {
        try {
            int updated = departmentService.patch(departmentId, fields);
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
    ResponseEntity<DepartmentDTO> getDepartmentByCode(
            @PathVariable String schoolId,
            @RequestParam String departmentCode
    ) {
        return ResponseEntity.ok(departmentService.fetchOneByCustomColumn(schoolId, departmentCode));
    }

    @GetMapping("/basics/{schoolId}")
    ResponseEntity<?> getDepartmentBasicValues(@PathVariable String schoolId) {
        return ResponseEntity.ok(departmentService.fetchAll(schoolId));
    }

}
