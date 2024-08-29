package com.edusyspro.api.controller;

import com.edusyspro.api.data.ConstantUtils;
import com.edusyspro.api.model.Score;
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
    ResponseEntity<Page<Score>> getAllScores(
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
    ResponseEntity<List<Score>> getAllScores(
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
}
