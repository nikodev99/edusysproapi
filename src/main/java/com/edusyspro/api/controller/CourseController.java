package com.edusyspro.api.controller;

import com.edusyspro.api.dto.CourseDTO;
import com.edusyspro.api.service.interfaces.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping({"", "/all"})
    ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.fetchAll());
    }
}
