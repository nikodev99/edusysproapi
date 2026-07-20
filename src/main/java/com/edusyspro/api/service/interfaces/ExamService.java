package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.ExamDTO;
import com.edusyspro.api.dto.custom.ExamProgress;
import com.edusyspro.api.dto.custom.ExamResponse;

import java.util.List;
import java.util.Map;

public interface ExamService {

    List<ExamDTO> findAllClasseExams(Integer classeId, String academicYear);

    ExamDTO findClasseExamsAssignments(Long examId, Integer classeId, String academicYear);

    ExamResponse findClasseExamWithCalculations(Long examId, Integer classeId, String academicYear, boolean onlyStat);
    List<ExamResponse> findClasseAllExamWithCalculations(Integer classeId, String academicYear, boolean onlyStat);

    List<ExamProgress> getStudentExamProgress(String studentId, Integer classeId, String academicYear);

    ExamDTO findStudentExamsAssignments(Long examId, Integer classeId, String academicYear, String studentId);

    ExamResponse findStudentExamWithCalculations(Long examId, Integer classeId, String academicYear, String studentId, boolean onlyStat);
    List<ExamResponse> findStudentExamWithCalculations(Integer classeId, String academicYear, String studentId, boolean onlyStat);

    ExamDTO findExamById(Long examId);

    List<ExamDTO> findAllSchoolExams(String schoolId, String academicYear);
}
