package com.edusyspro.api.controller;

import com.edusyspro.api.dto.DepartmentDTO;
import com.edusyspro.api.service.interfaces.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
