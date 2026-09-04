package com.edusyspro.api.controller;

import com.edusyspro.api.auth.response.MessageResponse;
import com.edusyspro.api.dto.ExamDTO;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.exception.sql.NotFountException;
import com.edusyspro.api.model.ExamType;
import com.edusyspro.api.service.interfaces.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/exam")
public class ExamController {

    private final ExamService examService;

    @Autowired
    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping
    ResponseEntity<?> saveExam(@RequestBody ExamDTO exam) {
        try {
            ExamDTO savedExam = examService.saveExam(exam);
            return ResponseEntity.ok(MessageResponse.builder()
                    .message("Examen ajouté avec succès")
                    .timestamp(Instant.now().toString())
                    .data(savedExam)
                    .build());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(MessageResponse.builder()
                    .message(e.getMessage())
                    .isError(true)
                    .timestamp(Instant.now().toString())
                    .build());
        }
    }

    @PostMapping("/type")
    ResponseEntity<?> saveExamType(@RequestBody ExamType exam) {
        try {
            ExamType savedExam = examService.saveExamType(exam);
            return ResponseEntity.ok(MessageResponse.builder()
                    .message("Le Template des examens a été ajouté avec succès")
                    .timestamp(Instant.now().toString())
                    .data(savedExam)
                    .build());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(MessageResponse.builder()
                    .message(e.getMessage())
                    .isError(true)
                    .timestamp(Instant.now().toString())
                    .build());
        }
    }

    @GetMapping("/{schoolId}")
    ResponseEntity<?> getAllExams(@PathVariable String schoolId, @RequestParam(required = false) String academicYear){
        return ResponseEntity.ok(examService.findAllSchoolExams(schoolId, academicYear));
    }

    @GetMapping("/types/{schoolId}")
    ResponseEntity<?> getAllExamType(@PathVariable String schoolId) {
        return ResponseEntity.ok(examService.findAllExamTypes(schoolId));
    }

    @GetMapping("/classe/{classeId}")
    ResponseEntity<?> getAllClasseExams(@PathVariable Integer classeId, @RequestParam String academicYear){
        return ResponseEntity.ok(examService.findAllClasseExams(classeId, academicYear));
    }

    @GetMapping("/{examId}/classe_{classeId}")
    ResponseEntity<?> getClasseExam(
            @PathVariable Integer examId,
            @PathVariable Integer classeId,
            @RequestParam String academicYear,
            @RequestParam(required = false) boolean onlyStat
    ){
        try {
            return ResponseEntity.ok(examService.findClasseExamWithCalculations(examId, classeId, academicYear, onlyStat));
        }catch (NotFountException n) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(n.getMessage());
        }
    }

    @GetMapping("/{examId}/{studentId}/{classeId}")
    ResponseEntity<?> getStudentExam(
            @PathVariable Integer examId,
            @PathVariable Integer classeId,
            @PathVariable String studentId,
            @RequestParam String academicYear,
            @RequestParam(required = false) boolean onlyStat
    ){
        try {
            return ResponseEntity.ok(examService.findStudentExamWithCalculations(examId, classeId, academicYear, studentId, onlyStat));
        }catch (NotFountException n) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(n.getMessage());
        }
    }

    @GetMapping("/progress/{studentId}/{classeId}")
    ResponseEntity<?> getStudentExamProgress(
            @PathVariable String studentId,
            @PathVariable Integer classeId,
            @RequestParam String academicYear
    ){
        try {
            return ResponseEntity.ok(examService.getStudentExamProgress(studentId, classeId, academicYear));
        }catch (NotFountException n) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(n.getMessage());
        }
    }

    @PatchMapping("/{examId}")
    ResponseEntity<?> updateExamFields(@PathVariable int examId, @RequestBody UpdateField fields) {
        try {
            int updated = examService.updateExam(examId, fields);
            if (updated > 0) {
                return ResponseEntity.ok("Modification " + fields.field() + " effective");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("School not found or update failed");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PatchMapping("/type/{examTypeId}")
    ResponseEntity<?> updateExamTypeFields(@PathVariable int examTypeId, @RequestBody UpdateField fields) {
        try {
            int updated = examService.updateExamType(examTypeId, fields);
            if (updated > 0) {
                return ResponseEntity.ok("Modification " + fields.field() + " effective");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("School not found or update failed");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{examId}")
    ResponseEntity<?> deleteExam(@PathVariable int examId) {
        try {
            return ResponseEntity.ok(examService.deleteExam(examId));
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/type/{examTypeId}")
    ResponseEntity<?> deleteExamType(@PathVariable int examTypeId) {
        try {
            return ResponseEntity.ok(examService.deleteExamType(examTypeId));
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
