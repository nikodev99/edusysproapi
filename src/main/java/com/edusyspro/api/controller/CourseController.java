package com.edusyspro.api.controller;

import com.edusyspro.api.controller.utils.ControllerUtils;
import com.edusyspro.api.data.ConstantUtils;
import com.edusyspro.api.dto.CourseDTO;
import com.edusyspro.api.exception.sql.AlreadyExistException;
import com.edusyspro.api.service.mod.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    ResponseEntity<?> insertCourse(@RequestBody CourseDTO course) {
        try {
            return ResponseEntity.ok(courseService.save(course));
        }catch (AlreadyExistException a) {
            return ResponseEntity.badRequest().body(a.getMessage());
        }
    }

    @GetMapping
    ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.findAllBasicCourses(ConstantUtils.SCHOOL_ID));
    }

    @GetMapping("/all")
    ResponseEntity<Page<CourseDTO>> getAllSchoolCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortCriteria
    ) {
        return ResponseEntity.ok(courseService.findAllSchoolCourses(
                ConstantUtils.SCHOOL_ID,
                ControllerUtils.setSort(page, size, sortCriteria)
        ));
    }

    @GetMapping("/search/")
    ResponseEntity<List<CourseDTO>> getAllSchoolCourses(@RequestParam String q) {
        return ResponseEntity.ok(courseService.findAllSchoolCourses(
                ConstantUtils.SCHOOL_ID,
                String.valueOf(q)
        ));
    }
}
