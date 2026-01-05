package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.ExamDTO;

import java.util.List;
import java.util.Map;

public interface ExamService {

    List<ExamDTO> findAllClasseExams(Integer classeId, String academicYear);

    ExamDTO findClasseExamsAssignments(Long examId, Integer classeId, String academicYear);

    Map<String, Object> findClasseExamWithCalculations(Long examId, Integer classeId, String academicYear);

    ExamDTO findStudentExamsAssignments(Long examId, Integer classeId, String academicYear, String studentId);

    Map<String, Object> findStudentExamWithCalculations(Long examId, Integer classeId, String academicYear, String studentId);

    ExamDTO findExamById(Long examId);

    List<ExamDTO> findAllSchoolExams(String schoolId, String academicYear);
}
