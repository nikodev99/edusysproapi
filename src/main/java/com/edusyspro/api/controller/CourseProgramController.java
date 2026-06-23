package com.edusyspro.api.controller;

import com.edusyspro.api.auth.response.MessageResponse;
import com.edusyspro.api.dto.CourseProgramDTO;
import com.edusyspro.api.dto.CourseProgramRequest;
import com.edusyspro.api.dto.CourseProgramTopicDTO;
import com.edusyspro.api.dto.custom.CourseAndClasseIds;
import com.edusyspro.api.dto.custom.CourseProgramResponse;
import com.edusyspro.api.exception.sql.NotFountException;
import com.edusyspro.api.model.enums.ProgramStatus;
import com.edusyspro.api.service.interfaces.CourseProgramService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/programs")
public class CourseProgramController {
    private final CourseProgramService courseProgramService;

    public CourseProgramController(CourseProgramService courseProgramService) {
        this.courseProgramService = courseProgramService;
    }

    @PostMapping
    ResponseEntity<MessageResponse> insertNewProgram(@RequestBody CourseProgramRequest program) {
        try {
            Long insertedId = courseProgramService.saveCourseProgram(program);
            return ResponseEntity.ok(MessageResponse.builder()
                            .message("Program inserted successfully. ID: " + insertedId + ".")
                            .isError(insertedId == 0)
                            .timestamp(Instant.now().toString())
                        .build());
        } catch (NotFountException e) {
            return ResponseEntity.badRequest().body(MessageResponse.builder()
                    .message(e.getMessage())
                    .isError(true)
                    .timestamp(Instant.now().toString())
                    .build());
        }
    }

    @PostMapping("/topic")
    ResponseEntity<MessageResponse> insertNewTopicToProgram(@RequestBody CourseProgramTopicDTO topic) {
        try {
            Long insertedId = courseProgramService.saveCourseProgramTopic(topic);
            return ResponseEntity.ok(MessageResponse.builder()
                    .message("Topic inserted successfully. ID: " + insertedId + ".")
                    .isError(insertedId == 0)
                    .timestamp(Instant.now().toString())
                    .build());
        } catch (NotFountException e) {
            return ResponseEntity.badRequest().body(MessageResponse.builder()
                    .message(e.getMessage())
                    .isError(true)
                    .timestamp(Instant.now().toString())
                    .build());
        }
    }

    @GetMapping("/course/{teacherId}")
    ResponseEntity<CourseProgramResponse> getAllTeacherPrograms(
            @PathVariable String teacherId,
            @RequestParam int classe,
            @RequestParam(required = false) Integer course,
            @RequestParam String academicYear
    ) {
        CourseAndClasseIds ids = new CourseAndClasseIds(course, classe);
        return ResponseEntity.ok(courseProgramService.findAllProgramsByTeacherCourseAndClasse(
                teacherId, ids, academicYear
        ));
    }

    @GetMapping("/{teacherId}")
    ResponseEntity<CourseProgramResponse> getAllTeacherPrograms(
            @PathVariable String teacherId,
            @RequestParam int classe,
            @RequestParam String academicYear
    ) {
        CourseAndClasseIds ids = new CourseAndClasseIds(0, classe);
        return ResponseEntity.ok(courseProgramService.findAllProgramsByTeacherAndClasse(
                teacherId, ids, academicYear
        ));
    }

    @PatchMapping("/{id}")
    ResponseEntity<MessageResponse> changeStatus(@PathVariable Long id, @RequestParam ProgramStatus status) {
        return ResponseEntity.ok(courseProgramService.changeStatus(id, status));
    }

    @PatchMapping("/complete/{id}")
    ResponseEntity<MessageResponse> completed(@PathVariable Long id) {
        return ResponseEntity.ok(courseProgramService.completed(id));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<MessageResponse> deleteProgram(@PathVariable Long id) {
        return ResponseEntity.ok(courseProgramService.deleteProgram(id));
    }

    @DeleteMapping("/topic/{id}")
    ResponseEntity<MessageResponse> deleteTopic(@PathVariable Long id) {
        return ResponseEntity.ok(courseProgramService.deleteTopic(id));
    }
}
