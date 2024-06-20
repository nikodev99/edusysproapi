package com.edusyspro.api.student.controller;

import com.edusyspro.api.student.models.dtos.EnrolledStudent;
import com.edusyspro.api.student.models.Enrollment;
import com.edusyspro.api.student.models.dtos.EnrolledStudentGuardian;
import com.edusyspro.api.student.services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/enroll")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    ResponseEntity<Enrollment> save(@RequestBody Enrollment enrollment) {
        System.out.println("Data to insert: " + enrollment);
        return ResponseEntity.ok(enrollmentService.enrollStudent(enrollment));
    }

    @GetMapping()
    ResponseEntity<List<EnrolledStudent>> getEnrolledStudents() {
        return ResponseEntity.ok(enrollmentService.getEnrolledStudents(UUID.fromString("19e8cf01-5098-453b-9d65-d57cd17fc548")));
    }

    @GetMapping("/guardians")
    ResponseEntity<List<EnrolledStudentGuardian>> fetchEnrolledStudentsGuardians() {
        return ResponseEntity.ok(
                enrollmentService.getEnrolledStudentGuardians(false, UUID.fromString("19e8cf01-5098-453b-9d65-d57cd17fc548"))
        );
    }
}
