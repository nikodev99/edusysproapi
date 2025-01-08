package com.edusyspro.api.controller;

import com.edusyspro.api.dto.ScoreDTO;
import com.edusyspro.api.service.interfaces.AcademicYearService;
import com.edusyspro.api.service.interfaces.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/score")
public class ScoreController {

    private final ScoreService scoreService;

    @Autowired
    public ScoreController(ScoreService scoreService, AcademicYearService academicYearService) {
        this.scoreService = scoreService;
    }

    @GetMapping({"/all/{studentId}", "/{studentId}"})
    ResponseEntity<Page<ScoreDTO>> getAllScores(
            @PathVariable String studentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String academicYearId) {
        return ResponseEntity.ok(scoreService.getScoresByStudentPerAcademicYear(
                studentId,
                academicYearId,
                PageRequest.of(page, size)
        ));
    }

    @GetMapping({"/all/{studentId}-{subjectId}", "/{studentId}-{subjectId}"})
    ResponseEntity<List<ScoreDTO>> getAllScores(
            @PathVariable String studentId,
            @RequestParam String academicYearId,
            @PathVariable int subjectId
    ) {
        return ResponseEntity.ok(scoreService.getScoresByStudentPerAcademicYear(
                studentId,
                academicYearId,
                subjectId
        ));
    }

    @GetMapping("/all_teacher_marks/{teacherId}")
    ResponseEntity<?> getAllTeacherMarks(@PathVariable String teacherId) {
        return ResponseEntity.ok(scoreService.getAllTeacherMarks(Long.parseLong(teacherId)));
    }

    @GetMapping("/all_assignment_marks/{assignmentId}")
    ResponseEntity<?> getAllAssignmentMarks(@PathVariable String assignmentId, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(scoreService.getAllAssignmentScores(
                Long.parseLong(assignmentId),
                PageRequest.of(0, size)
        ));
    }

    @GetMapping("/students/{teacherId}/{subjectId}")
    ResponseEntity<?> getBestStudentByMarks(@PathVariable long teacherId, @PathVariable int subjectId) {
        return ResponseEntity.ok(scoreService.getBestStudentBySubjectScore(teacherId, subjectId));
    }

    @GetMapping("/students/{teacherId}")
    ResponseEntity<?> getBestStudentByMarks(@PathVariable String teacherId) {
        return ResponseEntity.ok(scoreService.getBestStudentByScore(
                Long.parseLong(teacherId)
        ));
    }
}
