package com.edusyspro.api.controller;

import com.edusyspro.api.controller.utils.ControllerUtils;
import com.edusyspro.api.data.ConstantUtils;
import com.edusyspro.api.dto.Guardian;
import com.edusyspro.api.dto.EnrolledStudent;
import com.edusyspro.api.dto.Enrollment;
import com.edusyspro.api.service.interfaces.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.ok(enrollmentService.enrollStudent(enrollment));
    }

    @GetMapping()
    ResponseEntity<Page<EnrolledStudent>> getEnrolledStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortCriteria
    ) {
        Pageable pageable = ControllerUtils.setSort(page, size, sortCriteria);
        return ResponseEntity.ok(enrollmentService.getEnrolledStudents(ConstantUtils.SCHOOL_ID, pageable));
    }

    @GetMapping("/search/")
    ResponseEntity<List<EnrolledStudent>> getEnrolledStudents(@RequestParam String q) {
        return ResponseEntity.ok(enrollmentService.getEnrolledStudents(ConstantUtils.SCHOOL_ID, q));
    }

    @GetMapping("/student/{studentId}")
    ResponseEntity<Enrollment> getEnrollmentById(@PathVariable String studentId) {
        return ResponseEntity.ok(enrollmentService.getEnrolledStudent(ConstantUtils.SCHOOL_ID, studentId));
    }

    @GetMapping("/classmates/{studentId}-{classeId}")
    ResponseEntity<List<Enrollment>> getStudentClassmates(@PathVariable String studentId, @PathVariable int classeId) {
        return ResponseEntity.ok(enrollmentService.getStudentClassmates(ConstantUtils.SCHOOL_ID, studentId, classeId, 5));
    }

    @GetMapping("/{studentId}-{classeId}")
    ResponseEntity<Page<Enrollment>> getAllStudentClassmates(
            @PathVariable int classeId,
            @PathVariable String studentId,
            @RequestParam String academicYearId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(enrollmentService.getAllStudentClassmates(
                ConstantUtils.SCHOOL_ID, studentId, classeId, academicYearId, pageable
        ));
    }

    @GetMapping("/guardians")
    ResponseEntity<Page<Guardian>> fetchEnrolledStudentsGuardians(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortCriteria
    ) {
        Pageable pageable = ControllerUtils.setSort(page, size, sortCriteria);
        return ResponseEntity.ok(
                enrollmentService.getEnrolledStudentGuardians(ConstantUtils.SCHOOL_ID, pageable)
        );
    }

    @GetMapping("/guardians/search/")
    ResponseEntity<List<Guardian>> fetchEnrolledStudentsGuardians(@RequestParam String q) {
        return ResponseEntity.ok(enrollmentService.getEnrolledStudentGuardians(ConstantUtils.SCHOOL_ID, q));
    }
}
