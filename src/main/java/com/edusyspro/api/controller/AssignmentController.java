package com.edusyspro.api.controller;

import com.edusyspro.api.service.interfaces.AssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping("/teacher_some_{teacherId}")
    ResponseEntity<?> fetchSomeTeacherAssignments(@PathVariable String teacherId) {
        return ResponseEntity.ok(
                assignmentService.findSomeAssignmentsPreparedByTeacher(Long.parseLong(teacherId))
        );
    }
}
