package com.edusyspro.api.controller;

import com.edusyspro.api.data.ConstantUtils;
import com.edusyspro.api.dto.Guardian;
import com.edusyspro.api.dto.EnrolledStudent;
import com.edusyspro.api.dto.Enrollment;
import com.edusyspro.api.service.interfaces.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

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
    ResponseEntity<Page<List<EnrolledStudent>>> getEnrolledStudents(
            @RequestParam(defaultValue = "10") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortCriteria
    ) {
        Pageable pageable = PageRequest.of(page, size);
        if (sortCriteria != null && !sortCriteria.isEmpty()) {
            List<Sort.Order> orders = Stream.of(sortCriteria.split(","))
                    .map(criteria -> criteria.split(":"))
                    .map(c -> new Sort.Order(Sort.Direction.fromString(c[1]), c[0]))
                    .toList();
            pageable = PageRequest.of(page, size, Sort.by(orders));
        }
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

    @GetMapping("/student/{studentId}/classmate")
    ResponseEntity<List<EnrolledStudent>> getStudentClassmates(@PathVariable String studentId) {
        return ResponseEntity.ok(enrollmentService.getStudentClassmates(ConstantUtils.SCHOOL_ID, studentId, 5));
    }

    @GetMapping("/guardians")
    ResponseEntity<List<Guardian>> fetchEnrolledStudentsGuardians() {
        return ResponseEntity.ok(
                enrollmentService.getEnrolledStudentGuardians(ConstantUtils.SCHOOL_ID, false)
        );
    }
}
