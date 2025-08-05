package com.edusyspro.api.controller;

import com.edusyspro.api.dto.CourseProgramDTO;
import com.edusyspro.api.dto.custom.CourseAndClasseIds;
import com.edusyspro.api.service.interfaces.CourseProgramService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/programs")
public class CourseProgramController {
    private final CourseProgramService courseProgramService;

    public CourseProgramController(CourseProgramService courseProgramService) {
        this.courseProgramService = courseProgramService;
    }

    @GetMapping("/course/{teacherId}")
    ResponseEntity<List<CourseProgramDTO>> getAllTeacherPrograms(
            @PathVariable String teacherId,
            @RequestParam int classe,
            @RequestParam(required = false) int course
    ) {
        CourseAndClasseIds ids = new CourseAndClasseIds(course, classe);
        return ResponseEntity.ok(courseProgramService.findAllProgramsByTeacherCourseAndClasse(
                teacherId, ids
        ));
    }

    @GetMapping("/{teacherId}")
    ResponseEntity<List<CourseProgramDTO>> getAllTeacherPrograms(
            @PathVariable String teacherId,
            @RequestParam int classe
    ) {
        CourseAndClasseIds ids = new CourseAndClasseIds(0, classe);
        return ResponseEntity.ok(courseProgramService.findAllProgramsByTeacherAndClasse(
                teacherId, ids
        ));
    }
}
