package com.edusyspro.api.controller;

import com.edusyspro.api.dto.Student;
import com.edusyspro.api.dto.UpdateField;
import com.edusyspro.api.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateStudentByField(@PathVariable String id, @RequestBody UpdateField student) {
        try {
            int updated = studentService.updateStudent(student.field(), student.value(), id);
            if (updated > 0) {
                return ResponseEntity.ok("Modification effective");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found or update failed");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PatchMapping("/address/{addressId}")
    public ResponseEntity<String> updateStudentAddressByField(@PathVariable long addressId, @RequestBody UpdateField student) {
        try {
            int updated = studentService.updateStudentAddress(student.field(), student.value(), addressId);
            if (updated > 0) {
                return ResponseEntity.ok("Modification effective");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found or update failed");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PatchMapping("/info/{infoId}")
    public ResponseEntity<String> controlUpdateStudentPersonalInfo(@PathVariable long infoId, @RequestBody UpdateField student) {
        try {
            int updated = studentService.updateStudentPersonalInfo(student.field(), student.value(), infoId);
            if (updated > 0) {
                return ResponseEntity.ok("Modification effective");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Personal info not found or update failed");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PatchMapping("/health/{id}")
    public ResponseEntity<String> updateStudentHealthByField(@PathVariable String id, @RequestBody UpdateField student) {
        try {
            int updated = studentService.updateStudentHealth(student.field(), student.value(), id);
            if (updated > 0) {
                return ResponseEntity.ok("Modification effective");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Health Condition not found or update failed");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PatchMapping("/guardian/{guardianId}")
    public ResponseEntity<String> updateStudentGuardianByField(@PathVariable String guardianId, @RequestBody UpdateField student) {
        try {
            int updated = studentService.updateStudentGuardian(student.field(), student.value(), guardianId);
            if (updated > 0) {
                return ResponseEntity.ok("Modification effective");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Guardian not found or update failed");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
