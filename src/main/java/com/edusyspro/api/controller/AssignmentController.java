package com.edusyspro.api.controller;

import com.edusyspro.api.controller.utils.ControllerUtils;
import com.edusyspro.api.data.ConstantUtils;
import com.edusyspro.api.dto.AssignmentDTO;
import com.edusyspro.api.dto.custom.CourseAndClasseIds;
import com.edusyspro.api.dto.filters.AssignmentFilter;
import com.edusyspro.api.exception.sql.AlreadyExistException;
import com.edusyspro.api.service.interfaces.AssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping
    ResponseEntity<?> saveAssignment(@RequestBody AssignmentDTO assignment) {
        try {
            return ResponseEntity.ok(assignmentService.addNewAssignment(assignment));
        }catch (AlreadyExistException a) {
            return ResponseEntity.badRequest().body(a.getMessage());
        }
    }

    @GetMapping(value = {"", "/all"})
    ResponseEntity<?> fetchAllAssignments(
            @RequestParam String academicYear,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortCriteria,
            @RequestParam(required = false) Integer grade,
            @RequestParam(required = false) Integer semester,
            @RequestParam(required = false) Integer classe,
            @RequestParam(required = false) Integer course,
            @RequestParam(required = false) String search
    ) {
        return ResponseEntity.ok(assignmentService.findAllAssignments(
                new AssignmentFilter(
                        UUID.fromString(academicYear),
                        grade,
                        semester,
                        classe,
                        course,
                        search
                ),
                ControllerUtils.setSort(page, size, sortCriteria)
        ));
    }

    @GetMapping("/not_completed")
    ResponseEntity<?> fetchAllNotCompletedAssignments() {
        return ResponseEntity.ok(
                assignmentService.findAllNotCompleteAssignment(ConstantUtils.ACADEMIC_YEAR)
        );
    }

    @GetMapping("/classe/{classeId}")
    ResponseEntity<?> fetchClasseAssignments(@PathVariable Integer classeId, @RequestParam String academicYear) {
        return ResponseEntity.ok(assignmentService.findAllClasseAssignments(classeId, academicYear));
    }

    @GetMapping("/classe/{classeId}/{subjectId}")
    ResponseEntity<?> fetchClasseAssignments(
            @PathVariable Integer classeId,
            @PathVariable Integer subjectId,
            @RequestParam String academicYear
    ) {
        return ResponseEntity.ok(assignmentService.findAllClasseAssignmentsBySubject(classeId, academicYear, subjectId));
    }

    @GetMapping("/course/{courseId}")
    ResponseEntity<?> fetchAllCourseAssignments(
            @PathVariable Integer courseId,
            @RequestParam String academicYear
    ) {
        return ResponseEntity.ok(assignmentService.findAllCourseAssignments(courseId, academicYear));
    }

    @GetMapping("/teacher_some_{teacherId}")
    ResponseEntity<?> fetchSomeTeacherAssignments(@PathVariable String teacherId) {
        return ResponseEntity.ok(
                assignmentService.findSomeAssignmentsPreparedByTeacher(Long.parseLong(teacherId))
        );
    }

    @GetMapping("/teacher_all/{teacherId}")
    ResponseEntity<?> fetchAllTeacherAssignments(@PathVariable String teacherId) {
        return ResponseEntity.ok(
                assignmentService.findAllAssignmentsPreparedByTeacher(Long.parseLong(teacherId))
        );
    }

    @GetMapping("/teacher_all_course_{teacherId}")
    ResponseEntity<?> fetchAllTeacherAssignments(
            @PathVariable String teacherId,
            @RequestParam() int classe,
            @RequestParam() int course
    ) {
        return ResponseEntity.ok(
                assignmentService.findAllAssignmentsPreparedByTeacherByCourse(
                        Long.parseLong(teacherId), new CourseAndClasseIds(course, classe)
                )
        );
    }

    @GetMapping("/teacher_all_{teacherId}")
    ResponseEntity<?> fetchAllTeacherAssignments(
            @PathVariable String teacherId,
            @RequestParam() int classe
    ) {
        return ResponseEntity.ok(
                assignmentService.findAllAssignmentsPreparedByTeacher(
                        Long.parseLong(teacherId), new CourseAndClasseIds(0, classe)
                )
        );
    }

    @PutMapping("/change/{assignmentId}")
    ResponseEntity<?> changeAssignmentDates(@RequestBody AssignmentDTO assignment, @PathVariable long assignmentId) {
        return ResponseEntity.ok(assignmentService.updateAssignmentDates(assignment, assignmentId));
    }

    @DeleteMapping("/{assignmentId}")
    ResponseEntity<?> deleteAssignment(@PathVariable long assignmentId) {
        return ResponseEntity.ok(assignmentService.removeAssignment(assignmentId));
    }
}
