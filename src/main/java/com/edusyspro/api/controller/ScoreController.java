package com.edusyspro.api.controller;

import com.edusyspro.api.data.ConstantUtils;
import com.edusyspro.api.model.Score;
import com.edusyspro.api.service.interfaces.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/score")
public class ScoreController {

    private final ScoreService scoreService;

    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping("/all")
    ResponseEntity<Page<Score>> getAllScores(
            @RequestParam(defaultValue = "10") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String academicYearId
    ) {
        return ResponseEntity.ok(scoreService.getScoresByStudentPerAcademicYear(
                ConstantUtils.SCHOOL_ID,
                academicYearId,
                PageRequest.of(page, size)
        ));
    }
}
