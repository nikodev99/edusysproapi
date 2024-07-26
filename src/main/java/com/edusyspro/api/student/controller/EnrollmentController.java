package com.edusyspro.api.student.controller;

import com.edusyspro.api.data.ConstantUtils;
import com.edusyspro.api.student.models.Guardian;
import com.edusyspro.api.student.models.dtos.EnrolledStudent;
import com.edusyspro.api.student.models.Enrollment;
import com.edusyspro.api.student.services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
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
        return ResponseEntity.ok(enrollmentService.getEnrolledStudents(UUID.fromString(ConstantUtils.SCHOOL_ID), pageable));
    }

    @GetMapping("/search/")
    ResponseEntity<List<EnrolledStudent>> getEnrolledStudents(@RequestParam String q) {
        return ResponseEntity.ok(enrollmentService.getEnrolledStudents(UUID.fromString(ConstantUtils.SCHOOL_ID), q));
    }

    @GetMapping("/student/{id}")
    ResponseEntity<Enrollment> getEnrollmentById(@PathVariable String id) {
        return ResponseEntity.ok(enrollmentService.getEnrolledStudent(UUID.fromString(ConstantUtils.SCHOOL_ID), UUID.fromString(id)));
    }

    @GetMapping("/guardians")
    ResponseEntity<List<Guardian>> fetchEnrolledStudentsGuardians() {
        return ResponseEntity.ok(
                enrollmentService.getEnrolledStudentGuardians(UUID.fromString(ConstantUtils.SCHOOL_ID), false)
        );
    }
}
