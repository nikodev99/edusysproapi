package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.ExamDTO;

import java.util.List;

public interface ExamService {

    List<ExamDTO> findAllClasseExams(Integer classeId, String academicYear);

    ExamDTO findClasseExamsAssignments(Long examId, Integer classeId, String academicYear);

    ExamDTO findExamById(Long examId);

    List<ExamDTO> findAllSchoolExams(String schoolId, String academicYear);
}
