package com.edusyspro.api.controller;

import com.edusyspro.api.dto.AssignmentDTO;
import com.edusyspro.api.dto.custom.CourseAndClasseIds;
import com.edusyspro.api.service.interfaces.AssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
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
