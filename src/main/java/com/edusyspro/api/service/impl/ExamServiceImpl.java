package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.AssignmentDTO;
import com.edusyspro.api.dto.ExamDTO;
import com.edusyspro.api.dto.ScoreDTO;
import com.edusyspro.api.dto.custom.ExamEssential;
import com.edusyspro.api.exception.sql.NotFountException;
import com.edusyspro.api.repository.ExamRepository;
import com.edusyspro.api.service.interfaces.AssignmentService;
import com.edusyspro.api.service.interfaces.ExamService;
import com.edusyspro.api.service.interfaces.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;
    private final AssignmentService assignmentService;
    private final ScoreService scoreService;

    @Autowired
    public ExamServiceImpl(ExamRepository examRepository, AssignmentService assignmentService, ScoreService scoreService) {
        this.examRepository = examRepository;
        this.assignmentService = assignmentService;
        this.scoreService = scoreService;
    }

    @Override
    public List<ExamDTO> findAllClasseExams(Integer classeId, String academicYear) {
        return examRepository.findClasseExamsAssignments(classeId, UUID.fromString(academicYear)).stream()
                .map(ExamEssential::toDto)
                .toList();
    }

    @Override
    public ExamDTO findClasseExamsAssignments(Long examId, Integer classeId, String academicYear) {
       ExamDTO examDTO = examRepository.findClasseExamsAssignments(examId, classeId, UUID.fromString(academicYear))
               .orElseThrow(() -> new NotFountException("No Exam Found for ExamID: " + examId))
               .toDto();

       List<AssignmentDTO> assignments = assignmentService.findAllClasseExamAssignments(classeId, academicYear, examDTO.getId());
       return getExamAssignments(examDTO, assignments, null);
    }

    @Override
    public Map<String, Object> findClasseExamWithCalculations(Long examId, Integer classeId, String academicYear) {
        ExamDTO exam = examRepository.findClasseExamsAssignments(examId, classeId, UUID.fromString(academicYear))
                .orElseThrow(() -> new NotFountException("No Exam Found for ExamID: " + examId))
                .toDto();

        //Get all assignments for this exam
        List<AssignmentDTO> assignments = assignmentService.findAllClasseExamAssignments(classeId, academicYear, exam.getId());
    }

    @Override
    public ExamDTO findStudentExamsAssignments(Long examId, Integer classeId, String academicYear, String studentId) {
        ExamDTO exam = examRepository.findClasseExamsAssignments(examId, classeId, UUID.fromString(academicYear))
                .orElseThrow(() -> new NotFountException("No Exam Found for ExamID: " + examId))
                .toDto();

        List<AssignmentDTO> assignments = assignmentService.findAllStudentAssignmentsByExam(exam.getId(), classeId, academicYear, studentId);
        return getExamAssignments(exam, assignments, studentId);
    }

    @Override
    public Map<String, Object> findStudentExamWithCalculations(Long examId, Integer classeId, String academicYear, String studentId) {
        return Map.of();
    }

    @Override
    public ExamDTO findExamById(Long examId) {
        return examRepository.findExamById(examId)
                .orElseThrow(() -> new NotFountException("Exam Not found"))
                .toDto();
    }

    @Override
    public List<ExamDTO> findAllSchoolExams(String schoolId, String academicYear) {
        return examRepository.findAllSchoolExams(
                UUID.fromString(schoolId),
                UUID.fromString(academicYear)
        ).stream().map(ExamEssential::toDto).toList();
    }

    private ExamDTO getExamAssignments(ExamDTO exam, List<AssignmentDTO> assignments, String studentId) {
        if (!assignments.isEmpty()) {
            List<Long> assignmentIds = assignments.stream().map(AssignmentDTO::getId).toList();
            List<ScoreDTO> scores = studentId != null
                ? scoreService.getAssignmentScoresByStudent(assignmentIds, studentId)
                : scoreService.getAllAssignmentScores(assignmentIds);

            Map<Long, List<ScoreDTO>> scoresByAssignments = scores.stream()
                    .filter(score -> score.getAssignment() != null )
                    .collect(Collectors.groupingBy(score -> score.getAssignment().getId()));

            assignments.parallelStream()
                    .forEach(
                            assignment -> assignment.setMarks(scoresByAssignments.getOrDefault(assignment.getId(), Collections.emptyList()))
                    );

            exam.setAssignments(assignments);
        }
        return exam;
    }
}
