package com.edusyspro.api.controller;

import com.edusyspro.api.controller.utils.ControllerUtils;
import com.edusyspro.api.dto.EmployeeDTO;
import com.edusyspro.api.model.Employee;
import com.edusyspro.api.service.interfaces.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    ResponseEntity<?> saveEmployee(@RequestBody Employee employee) {
        System.out.println("EMPLOYEE: " + employee);
        return ResponseEntity.ok(employeeService.saveEmployee(employee));
    }

    @GetMapping("/all/{schoolId}")
    ResponseEntity<?> getPaginatedEmployees(
            @PathVariable String schoolId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortCriteria
    ) {
        Pageable pageable = ControllerUtils.setSort(page, size, sortCriteria);
        return ResponseEntity.ok(employeeService.findAllEmployees(schoolId, pageable));
    }

    @GetMapping("/search/{schoolId}")
    ResponseEntity<?> getSearchedEmployees(
            @PathVariable String schoolId,
            @RequestParam String query
    ) {
        return ResponseEntity.ok(employeeService.findAllSearchedEmployees(schoolId, query));
    }

    @GetMapping("/{id}")
    ResponseEntity<EmployeeDTO> getEmployee(@PathVariable String id) {
        return ResponseEntity.ok(employeeService.findEmployee(id));
    }
}
