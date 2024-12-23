package com.edusyspro.api.controller;

import com.edusyspro.api.controller.utils.ControllerUtils;
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

    @GetMapping("/teacher_some_{teacherId}")
    ResponseEntity<?> fetchSomeTeacherAssignments(@PathVariable String teacherId) {
        return ResponseEntity.ok(
                assignmentService.findSomeAssignmentsPreparedByTeacher(Long.parseLong(teacherId))
        );
    }

    @GetMapping("/teacher_all_{teacherId}")
    ResponseEntity<?> fetchAllTeacherAssignments(
            @PathVariable String teacherId,
            @RequestParam() int classe,
            @RequestParam() int course,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                assignmentService.findAllAssignmentsPreparedByTeacher(
                        Long.parseLong(teacherId), new CourseAndClasseIds(course, classe), ControllerUtils.setPage(page, size)
                )
        );
    }
}
