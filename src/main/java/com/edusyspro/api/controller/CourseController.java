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
import java.util.Map;

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

    @GetMapping("/basic/{schoolId}")
    ResponseEntity<List<CourseDTO>> getAllCourses(@PathVariable String schoolId) {
        return ResponseEntity.ok(courseService.findAllBasicCourses(schoolId));
    }

    @GetMapping("/all/{schoolId}")
    ResponseEntity<Page<CourseDTO>> getAllSchoolCourses(
            @PathVariable String schoolId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortCriteria
    ) {
        return ResponseEntity.ok(courseService.findAllSchoolCourses(
                schoolId, ControllerUtils.setSort(page, size, sortCriteria)
        ));
    }

    @GetMapping("/search/{schoolId}")
    ResponseEntity<List<CourseDTO>> getAllSchoolCourses(@PathVariable String schoolId, @RequestParam String q) {
        return ResponseEntity.ok(courseService.findAllSchoolCourses(
                schoolId, String.valueOf(q)
        ));
    }

    @GetMapping("/{courseId}")
    ResponseEntity<CourseDTO> getCourseById(@PathVariable Integer courseId) {
        return ResponseEntity.ok(courseService.findCourse(courseId));
    }

    @GetMapping("/count/{courseId}")
    ResponseEntity<?> countByCourseId(@PathVariable Integer courseId) {
        return ResponseEntity.ok(courseService.count(courseId));
    }

    @PutMapping("/{courseId}")
    ResponseEntity<?> updateCourse(@PathVariable Integer courseId, @RequestBody CourseDTO course) {
        try {
            Map<String, Boolean> hasUpdate = courseService.update(course, courseId);
            if (hasUpdate.containsKey("updated"))
                return ResponseEntity.ok(Map.of(
                        "updated",
                        "Mise à jour de la matière " + course.getCourse() + " effective.")
                );
        }catch (AlreadyExistException a) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error",
                    a.getMessage()
            ));
        }
        return null;
    }
}
