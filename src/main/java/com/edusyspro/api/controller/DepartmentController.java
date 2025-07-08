package com.edusyspro.api.controller;

import com.edusyspro.api.data.ConstantUtils;
import com.edusyspro.api.dto.DepartmentDTO;
import com.edusyspro.api.service.interfaces.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    ResponseEntity<DepartmentDTO> getDepartmentByCode(@RequestParam String departmentCode) {
        return ResponseEntity.ok(departmentService.fetchOneByCustomColumn(departmentCode));
    }

    @GetMapping("/basics/{schoolId}")
    ResponseEntity<?> getDepartmentBasicValues(@PathVariable String schoolId) {
        return ResponseEntity.ok(departmentService.fetchAll(schoolId));
    }

}
