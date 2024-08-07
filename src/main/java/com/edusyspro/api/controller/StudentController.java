package com.edusyspro.api.controller;

import com.edusyspro.api.dto.Student;
import com.edusyspro.api.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    ResponseEntity<Student> fetchStudentById(@PathVariable String id) {
        return ResponseEntity.ok(studentService.findStudentById(id));
    }
}
