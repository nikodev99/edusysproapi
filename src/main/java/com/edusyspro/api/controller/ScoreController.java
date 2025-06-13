package com.edusyspro.api.controller;

import com.edusyspro.api.dto.ScoreDTO;
import com.edusyspro.api.service.interfaces.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/score")
public class ScoreController {

    private final ScoreService scoreService;

    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PostMapping
    ResponseEntity<Boolean> saveScores(@RequestBody List<ScoreDTO> scoreDTOs, @RequestParam Long assignment) {
        return ResponseEntity.ok(scoreService.saveAllScores(scoreDTOs, assignment));
    }

    @PutMapping
    ResponseEntity<Boolean> modifyingScores(@RequestBody List<ScoreDTO> scoreDTOs, @RequestParam Long assignment) {
        return ResponseEntity.ok(scoreService.updateAllScores(scoreDTOs, assignment));
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

    @GetMapping({"/all/{studentId}/{subjectId}", "/{studentId}/{subjectId}"})
    ResponseEntity<List<ScoreDTO>> getAllScores(
            @PathVariable String studentId,
            @PathVariable int subjectId,
            @RequestParam String academicYearId
    ) {
        return ResponseEntity.ok(scoreService.getScoresByStudentPerSubjectPerAcademicYear(
                studentId,
                academicYearId,
                subjectId
        ));
    }

    @GetMapping("/all_teacher_marks/{teacherId}")
    ResponseEntity<?> getAllTeacherMarks(@PathVariable String teacherId) {
        boolean isMultiple = teacherId.contains(",");
        List<Long> teacherIds = Arrays.stream(teacherId.split(","))
                .map(Long::parseLong)
                .toList();
        return ResponseEntity.ok(scoreService.getAllTeacherMarks(
                isMultiple ? null : teacherIds.get(0),
                teacherIds
        ));
    }

    @GetMapping("/all_assignment_marks/{assignmentId}")
    ResponseEntity<?> getAllAssignmentMarks(@PathVariable String assignmentId, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(scoreService.getAllAssignmentScores(
                Long.parseLong(assignmentId),
                PageRequest.of(0, size)
        ));
    }

    @GetMapping("/assignment/{assignmentId}")
    ResponseEntity<?> getAllAssignmentMarks(@PathVariable String assignmentId) {
        return ResponseEntity.ok(scoreService.getAssignmentScores(Long.parseLong(assignmentId)));
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

    @GetMapping("/classe_best/{classeId}")
    ResponseEntity<?> getBestClasseStudentByMarks(@PathVariable int classeId, @RequestParam String academicYear) {
        return ResponseEntity.ok(scoreService.getClasseBestStudents(classeId, academicYear));
    }

    @GetMapping("/classe_best/{classeId}/{courseId}")
    ResponseEntity<?> getBestClasseStudentByMarks(
            @PathVariable int classeId,
            @RequestParam String academicYear,
            @PathVariable int courseId
    ) {
        return ResponseEntity.ok(scoreService.getClasseBestStudentsByCourse(classeId, academicYear, courseId));
    }

    @GetMapping("/classe_poor/{classeId}")
    ResponseEntity<?> getPoorClasseStudentByMarks(@PathVariable int classeId, @RequestParam String academicYear) {
        return ResponseEntity.ok(scoreService.getClassePoorStudents(classeId, academicYear));
    }

    @GetMapping("/course_best/{courseId}")
    ResponseEntity<?> getBestCourseStudentByMarks(@PathVariable int courseId, @RequestParam String academicYear) {
        return ResponseEntity.ok(scoreService.getCourseBestStudents(courseId, academicYear));
    }

    @GetMapping("/course_poor/{classeId}")
    ResponseEntity<?> getPoorCourseStudentByMarks(@PathVariable int classeId, @RequestParam String academicYear) {
        return ResponseEntity.ok(scoreService.getCoursePoorStudents(classeId, academicYear));
    }

    @GetMapping("/classe_avg/{classeId}")
    ResponseEntity<?> getScoreAvgByCourse(@PathVariable int classeId, @RequestParam String academicYear) {
        return ResponseEntity.ok(scoreService.getClasseAvgScore(classeId, academicYear));
    }

    @GetMapping("/count/{assignmentId}")
    ResponseEntity<Long> countAssignmentScores(@PathVariable long assignmentId) {
        return ResponseEntity.ok(scoreService.countAssignmentSCores(assignmentId));
    }
}
