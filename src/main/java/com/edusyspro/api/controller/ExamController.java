package com.edusyspro.api.controller;

import com.edusyspro.api.exception.sql.NotFountException;
import com.edusyspro.api.service.interfaces.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exam")
public class ExamController {

    private final ExamService examService;

    @Autowired
    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping("/classe/{classeId}")
    ResponseEntity<?> getAllClasseExams(@PathVariable Integer classeId, @RequestParam String academicYear){
        return ResponseEntity.ok(examService.findAllClasseExams(classeId, academicYear));
    }

    @GetMapping("/{examId}/classe_{classeId}")
    ResponseEntity<?> getClasseExam(
            @PathVariable Long examId,
            @PathVariable Integer classeId,
            @RequestParam String academicYear
    ){
        try {
            return ResponseEntity.ok(examService.findClasseExamsAssignments(examId, classeId, academicYear));
        }catch (NotFountException n) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(n.getMessage());
        }
    }
}
