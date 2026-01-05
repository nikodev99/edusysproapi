package com.edusyspro.api.controller;

import com.edusyspro.api.auth.response.MessageResponse;
import com.edusyspro.api.controller.utils.ControllerUtils;
import com.edusyspro.api.data.ConstantUtils;
import com.edusyspro.api.dto.EnrollmentDTO;
import com.edusyspro.api.dto.GuardianDTO;
import com.edusyspro.api.service.interfaces.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Arrays;
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
    ResponseEntity<?> save(@RequestBody EnrollmentDTO enrollmentDTO) {
        try {
            EnrollmentDTO saved = enrollmentService.enrollStudent(enrollmentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                    MessageResponse.builder()
                            .message(e.getMessage())
                            .timestamp(Instant.now().toString())
                            .build()
            );
        }
    }

    /**
     * Retrieves a paginated list of enrolled students.
     *
     * @param page the page number to retrieve, default value is 0
     * @param size the number of records per page, the default value is 10
     * @param sortCriteria optional sorting criteria to order the results
     * @return a {@link ResponseEntity} containing a {@link Page} of {@link EnrollmentDTO} objects
     */
    @GetMapping("/{schoolId}")
    ResponseEntity<Page<EnrollmentDTO>> getEnrolledStudents(
            @PathVariable String schoolId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortCriteria
    ) {
        Pageable pageable = ControllerUtils.setSort(page, size, sortCriteria);
        return ResponseEntity.ok(enrollmentService.getEnrolledStudents(schoolId, pageable));
    }

    /**
     * Retrieves a list of enrolled students based on the specified search query.
     *
     * @param q the search query to filter enrolled students
     * @return a {@link ResponseEntity} containing a {@link List} of {@link EnrollmentDTO} objects matching the query
     */
    @GetMapping("/search/{schoolId}")
    ResponseEntity<List<EnrollmentDTO>> getEnrolledStudents(@PathVariable String schoolId, @RequestParam String q) {
        return ResponseEntity.ok(enrollmentService.getEnrolledStudents(schoolId, q));
    }

    @GetMapping("/{schoolId}/{teacherId}")
    ResponseEntity<Page<EnrollmentDTO>> getEnrolledStudentsByTeacher(
            @PathVariable String schoolId,
            @PathVariable String teacherId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortCriteria
    ) {
        Pageable pageable = ControllerUtils.setSort(page, size, sortCriteria);
        return ResponseEntity.ok(
                enrollmentService.getEnrolledStudentByTeacherClasses(schoolId, teacherId, pageable)
        );
    }

    @GetMapping("/search/{schoolId}/{teacherId}")
    ResponseEntity<List<EnrollmentDTO>> getEnrolledStudentsByTeacher(
            @PathVariable String schoolId,
            @PathVariable String teacherId,
            @RequestParam String query
    ) {
        return ResponseEntity.ok(
                enrollmentService.getEnrolledStudentByTeacherClasses(schoolId, teacherId, query)
        );
    }

    @GetMapping("/not_enrolled/{schoolId}")
    ResponseEntity<?> getNotEnrolledStudents(@PathVariable String schoolId, @RequestParam String query) {
        return ResponseEntity.ok(enrollmentService.getUnenrolledStudents(schoolId, query));
    }

    @GetMapping("/searched-student/{schoolId}")
    ResponseEntity<?> getSearchedEnroll(@PathVariable String schoolId, @RequestParam String query) {
        String normalized = query
                .replace("+", " ")
                .trim()
                .replaceAll("\\s+", " ");
        return ResponseEntity.ok(enrollmentService.getSearchedStudent(schoolId, normalized));
    }

    /**
     * Retrieves enrollment details for a specific student based on the provided student ID.
     *
     * @param studentId the unique identifier of the student whose enrollment details are to be retrieved
     * @return a {@link ResponseEntity} containing the enrollment details encapsulated in an {@link EnrollmentDTO} object
     */
    @GetMapping("/student/{studentId}")
    ResponseEntity<EnrollmentDTO> getEnrollmentById(@PathVariable String studentId) {
        return ResponseEntity.ok(enrollmentService.getEnrolledStudent(studentId));
    }

    /**
     * Retrieves a paginated list of enrolled students for a specific class and academic year.
     *
     * @param classeId the unique identifier of the class whose enrolled students are to be retrieved
     * @param page the page number to retrieve, default value is 0
     * @param size the number of records per page, the default value is 10
     * @param sortCriteria optional sorting criteria to order the results
     * @param academicYear the academic year to filter the enrolled students
     * @return a {@link ResponseEntity} containing a {@link Page} of {@link EnrollmentDTO} objects
     */
    @GetMapping("/classroom/{classeId}")
    ResponseEntity<Page<EnrollmentDTO>> getClasseEnrolledStudents(
            @PathVariable int classeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortCriteria,
            @RequestParam String academicYear
    ) {
        Pageable pageable = ControllerUtils.setSort(page, size, sortCriteria);
        return ResponseEntity.ok(enrollmentService.getClasseEnrolledStudents(classeId, academicYear, pageable));
    }

    /**
     * Retrieves a list of enrolled students for a specific class and academic year.
     *
     * @param classeId the unique identifier of the class whose enrolled students are to be retrieved
     * @param academicYear the academic year to filter the enrolled students
     * @return a {@link ResponseEntity} containing a {@link List} of {@link EnrollmentDTO} objects
     */
    @GetMapping("/classe/{classeId}")
    ResponseEntity<List<EnrollmentDTO>> getClasseEnrolledStudents(
            @PathVariable int classeId,
            @RequestParam String academicYear
    ) {
        return ResponseEntity.ok(enrollmentService.getClasseEnrolledStudents(classeId, academicYear));
    }

    /**
     * Retrieves a list of enrolled students for a specific class and academic year
     * based on the provided search query.
     *
     * @param classeId the unique identifier of the class whose enrolled students are to be retrieved
     * @param academicYear the academic year to filter the enrolled students
     * @param search the search query to filter the enrolled students
     * @return a {@link ResponseEntity} containing a {@link List} of {@link EnrollmentDTO} objects
     */
    @GetMapping("/classroom_search/{classeId}")
    ResponseEntity<List<EnrollmentDTO>> getClasseEnrolledStudents(
            @PathVariable int classeId,
            @RequestParam String academicYear,
            @RequestParam String search
    ) {
        return ResponseEntity.ok(enrollmentService.getClasseEnrolledStudentsSearch(classeId, academicYear, search));
    }

    /**
     * Retrieves a list of classmates for a specific student in a specified class.
     *
     * @param studentId the unique identifier of the student whose classmates are to be retrieved
     * @param classeId the unique identifier of the class from which to retrieve the classmates
     * @return a {@link ResponseEntity} containing a {@link List} of {@link EnrollmentDTO} objects representing the classmates
     */
    @GetMapping("/classmates/{schoolId}/{studentId}_{classeId}")
    ResponseEntity<List<EnrollmentDTO>> getStudentClassmates(
            @PathVariable String schoolId,
            @PathVariable String studentId,
            @PathVariable int classeId
    ) {
        return ResponseEntity.ok(enrollmentService.getStudentClassmates(schoolId, studentId, classeId, 5));
    }

    /**
     * Retrieves a paginated list of classmates for a specific student in a given class and academic year.
     *
     * @param classeId the unique identifier of the class from which to retrieve the classmates
     * @param studentId the unique identifier of the student whose classmates are to be retrieved
     * @param academicYearId the unique identifier of the academic year used for filtering the classmates
     * @param page the page number to retrieve, default value is 0
     * @param size the number of records per page, the default value is 10
     * @return a {@link ResponseEntity} containing a {@link Page} of {@link EnrollmentDTO} objects representing the classmates
     */
    @GetMapping("/classmates/{studentId}_{classeId}")
    ResponseEntity<Page<EnrollmentDTO>> getAllStudentClassmates(
            @PathVariable int classeId,
            @PathVariable String studentId,
            @RequestParam String academicYearId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(enrollmentService.getAllStudentClassmates(
                studentId, classeId, academicYearId, pageable
        ));
    }

    /**
     * Fetches a paginated list of guardians for enrolled students.
     *
     * @param page the page number to retrieve, default is 0
     * @param size the number of records per page, default is 10
     * @param sortCriteria an optional parameter to specify the sorting criteria
     * @return a ResponseEntity containing a Page of GuardianDTO objects
     */
    @GetMapping("/guardians/{schoolId}")
    ResponseEntity<Page<GuardianDTO>> fetchEnrolledStudentsGuardians(
            @PathVariable String schoolId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortCriteria
    ) {
        Pageable pageable = ControllerUtils.setSort(page, size, sortCriteria);
        return ResponseEntity.ok(
                enrollmentService.getEnrolledStudentGuardians(schoolId, pageable)
        );
    }

    /**
     * Fetches the list of guardians for students enrolled in the school based on search criteria.
     *
     * @param q the query parameter used for filtering guardians by specific criteria.
     * @return a ResponseEntity containing a list of GuardianDTO objects that match the query.
     */
    @GetMapping("/guardians/search/{schoolId}")
    ResponseEntity<List<GuardianDTO>> fetchEnrolledStudentsGuardians(@PathVariable String schoolId, @RequestParam String q) {
        return ResponseEntity.ok(enrollmentService.getEnrolledStudentGuardians(schoolId, q));
    }

    /**
     * Counts the number of students in a specific class for a given academic year.
     *
     * @param classeId the ID of the class for which student count is to be retrieved
     * @param academicYear the academic year for which the student count is to be calculated
     * @return a ResponseEntity containing the count of students in the specified class and academic year
     */
    @GetMapping("/count_classe/{classeId}")
    ResponseEntity<?> countStudents(@PathVariable int classeId, @RequestParam String academicYear) {
        return ResponseEntity.ok(enrollmentService.countClasseStudents(classeId, academicYear));
    }

    /**
     * Counts the number of students in the specified classes for a given academic year.
     *
     * @param classeIds A comma-separated string of class IDs.
     * @param academicYear The academic year for which the student count is calculated.
     * @return A ResponseEntity containing the count of students.
     */
    @GetMapping("/count/classe/{classeIds}")
    ResponseEntity<?> countStudents(@PathVariable String classeIds, @RequestParam String academicYear) {
        List<Integer> classIdList = Arrays.stream(classeIds.split(","))
                .map(Integer::parseInt)
                .toList();
        return ResponseEntity.ok(enrollmentService.countClasseStudents(classIdList, academicYear));
    }

    /**
     * Handles an HTTP GET request to count the number of students enrolled
     * in a specific school using the configured school ID.
     *
     * @return ResponseEntity containing the total count of students
     *         enrolled in the school as the response body.
     */
    @GetMapping("/count_school/{schoolId}")
    ResponseEntity<?> countStudents(@PathVariable String schoolId) {
        return ResponseEntity.ok(enrollmentService.countStudents(schoolId));
    }
}
